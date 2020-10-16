package com.mediamath.bidder;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.mediamath.bidder.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import static com.mediamath.adx.AdxExchange.OBJECT_MAPPER;

public class BidderService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BidderService.class);

    private LabelRepository labelRepository;
    private CopyOnWriteArrayList<Label> labels;
    private HttpClientService delphiClientService;

    public BidderService(LabelRepository labelRepository,
                         HttpClientService delphiClientService) {
        this.labelRepository = labelRepository;
        this.delphiClientService = delphiClientService;
        labels = new CopyOnWriteArrayList<>();
    }

    @PostConstruct
    public void refreshLabels() {
        Executors.newCachedThreadPool().submit(() -> {
            while(true) {
                labels.clear();
                labelRepository.findAll().forEach(i -> labels.add(i));
                LOGGER.info("labels refreshed size : {}", labels.size());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void process(VideoPayload vp) throws IOException {
        log(vp);
        callDelphi(vp);
    }

    private void callDelphi(VideoPayload vp) throws IOException {
        DelphiRequest delphiRequest = new DelphiRequest(vp);
        labels.forEach(label -> {
            if (label.getOperation().equals(Operation.CALL_DELPHI) && label.getSource().equals(Source.OPEN_RTB) && label.getEnabled().equals(Active.TRUE)) {
                try {
                    delphiRequest.getLabelEntries().put(label.getId(), new LabelEntry(label, getLabelValueFromOpenRtb(label, vp)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        String delphiReq = OBJECT_MAPPER.writeValueAsString(delphiRequest);
        String delphiResponse = delphiClientService.sendPostRequestWithJsonBody("/bidPrice", delphiReq, HttpClientService.PathOption.RELATIVE);
        FileUtils.writeStringToFile(new File("/etc/dynamic-label-demo/delphi.log"), delphiReq + "\t" + delphiResponse + "\n", Charset.defaultCharset(), true);
    }

    private void log(VideoPayload vp) throws IOException {
        FileUtils.writeStringToFile(new File("/etc/dynamic-label-demo/impression.log"), toRecord(vp), Charset.defaultCharset(), true);
    }

    private String toRecord(VideoPayload vp) throws JsonProcessingException {
        return vp.getId() + "\t" + vp.getSite().getId() + "\t" + getLabelColumnMap(vp) + "\n";
    }

    private String getLabelColumn(VideoPayload vp) throws JsonProcessingException {
        List<LabelEntry> labelEntries = new ArrayList<>();
        labels.forEach(label -> {
            if (label.getOperation().equals(Operation.LOG) && label.getSource().equals(Source.OPEN_RTB) && label.getEnabled().equals(Active.TRUE)) {
                try {
                    labelEntries.add(new LabelEntry(label, getLabelValueFromOpenRtb(label, vp)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return new ObjectMapper().writeValueAsString(labelEntries);
    }

    private String getLabelColumnMap(VideoPayload vp) throws JsonProcessingException {
        Map<Integer, LabelEntry> labelEntryMap = new HashMap<>();
        labels.forEach(label -> {
            if (label.getOperation().equals(Operation.LOG) && label.getSource().equals(Source.OPEN_RTB) && label.getEnabled().equals(Active.TRUE)) {
                try {
                    labelEntryMap.put(label.getId(), new LabelEntry(label, getLabelValueFromOpenRtb(label, vp)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return new ObjectMapper().writeValueAsString(labelEntryMap);
    }

    private String getLabelValueFromOpenRtb(Label label, VideoPayload vp) throws IOException {
        JsonParser parser = new JsonFactory().createParser(new ObjectMapper().writeValueAsString(vp));
        parser.setCodec(new ObjectMapper());
        TreeNode tree = parser.readValueAsTree();
        String nodeName = "/" + label.getField().replaceAll("\\.", "/");
        if (label.getField().startsWith("imp.")) {
            TreeNode impNode = tree.at("/imp").get(0);
            if (impNode != null) {
                nodeName = label.getField().replaceAll("imp.", "/");
            }
        }
        TreeNode foundNode = tree.at(nodeName);
        String value = getValueFromSimpleNode(foundNode, nodeName, label.getField());
        if (value == null) {
            if (foundNode instanceof ArrayNode) {
                ArrayNode node = (ArrayNode) foundNode;
                List<String> values = new ArrayList<>();
                for (int i = 0; i < node.size(); i++) {
                    String valueChild = getValueFromSimpleNode(node.get(i), nodeName, label.getField());
                    if (valueChild != null) {
                        values.add(valueChild);
                    }
                }
                value = OBJECT_MAPPER.writeValueAsString(values);
            } else if (foundNode instanceof ObjectNode) {
                ObjectNode node = (ObjectNode) foundNode;
                value = node.toString();
            }
        }
        return value;
    }

    private String getValueFromSimpleNode(TreeNode foundNode, String nodeName, String fieldName) {
        String value = null;
        if (foundNode instanceof TextNode) {
            TextNode node = (TextNode) foundNode;
            value = node.textValue();
        } if (foundNode instanceof NullNode) {
            value = "";
        } else if (foundNode instanceof BooleanNode) {
            BooleanNode node = (BooleanNode) foundNode;
            value = String.valueOf(node.booleanValue());
        } else if (foundNode instanceof FloatNode) {
            FloatNode node = (FloatNode) foundNode;
            value = String.valueOf(node.floatValue());
        } else if (foundNode instanceof DoubleNode) {
            DoubleNode node = (DoubleNode) foundNode;
            value = String.valueOf(node.doubleValue());
        } else if (foundNode instanceof DecimalNode) {
            DecimalNode node = (DecimalNode) foundNode;
            value = String.valueOf(node.decimalValue());
        } else if (foundNode instanceof IntNode) {
            IntNode node = (IntNode) foundNode;
            value = String.valueOf(node.intValue());
        }
        LOGGER.info("nodeName : {} , label name : {}, label value : {}", nodeName, fieldName, value);
        return value;
    }
}

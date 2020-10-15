package com.mediamath.bidder;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediamath.bidder.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import static com.mediamath.adx.AdxExchange.OBJECT_MAPPER;

public class BidderService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BidderService.class);

    private LabelRepository labelRepository;
    private CopyOnWriteArrayList<Label> labels;
    private HttpClientService delphiClientService;

    public BidderService(LabelRepository labelRepository) {
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
                    Thread.sleep(10000);
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
//        String delphiReq = OBJECT_MAPPER.writeValueAsString(getDelphiRequest(vp));
//        LOGGER.info("sending delphi request : {}", delphiReq);
//        delphiClientService.sendPostRequestWithJsonBody("/bidPrice", delphiReq, HttpClientService.PathOption.RELATIVE);
    }

//    private DelphiRequest getDelphiRequest(VideoPayload vp) {
//        DelphiRequest delphiRequest = new DelphiRequest(vp);
//    }

    private void log(VideoPayload vp) throws IOException {
        FileUtils.writeStringToFile(new File("/etc/dynamic-label-demo/impression.log"), toRecord(vp), Charset.defaultCharset(), true);
    }

    private String toRecord(VideoPayload vp) throws JsonProcessingException {
        return vp.getId() + "\t" + vp.getSite().getId() + "\t" + getlabelColumn(vp) + "\n";
    }

    private String getlabelColumn(VideoPayload vp) throws JsonProcessingException {
        LabelLogColumn column = new LabelLogColumn();
        labels.forEach(label -> {
            if (label.getOperation().equals(Operation.LOG) && label.getSource().equals(Source.OPEN_RTB)) {
                try {
                    column.addEntry(new LabelLogEntry(label, getLabelValueFromOpenRtb(label, vp)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return new ObjectMapper().writeValueAsString(column);
    }

    private String getLabelValueFromOpenRtb(Label label, VideoPayload vp) throws IOException {
        JsonParser parser = new JsonFactory().createParser(new ObjectMapper().writeValueAsString(vp));
        parser.setCodec(new ObjectMapper());
        TreeNode tree = parser.readValueAsTree();
        String nodeName = "/" + label.getField().replaceAll("\\.", "/");
        TreeNode node = tree.at(nodeName);
        String value = node.isMissingNode() ? "" : node.toString();
        LOGGER.info("nodeName : {} , label name : {}, label value : {}", nodeName, label.getField(), value);
        return value;
    }
}

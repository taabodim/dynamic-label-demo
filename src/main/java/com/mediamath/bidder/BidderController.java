package com.mediamath.bidder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediamath.bidder.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class BidderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidderController.class);

    private BidderService bidderService;
    private ObjectMapper objectMapper;
    private LabelRepository labelRepository;

    @Autowired
    public BidderController(
            ObjectMapper objectMapper,
            BidderService bidderService,
            LabelRepository labelRepository) {
        this.objectMapper = objectMapper;
        this.bidderService = bidderService;
        this.labelRepository = labelRepository;
    }

    @PostMapping("/bid")
    public void processBid(
            @RequestBody VideoPayload payload) throws IOException {
//        VideoPayload vp = objectMapper.readValue(payload, VideoPayload.class);
        LOGGER.info("payload received : {}", payload);
        bidderService.process(payload);
    }

    @GetMapping("/api/label/list")
    public Iterable<Label> listLabels() {
        return labelRepository.findAll();
    }

    @PostMapping("/api/label/update")
    public Iterable<Label> updateLabel(@RequestBody UpdateLabelPayload payload) {
        Optional<Label> labelInDbOpt = labelRepository.findById(payload.getId());
        if (labelInDbOpt.isPresent()) {
            Label labelInDb = labelInDbOpt.get();
            labelInDb.setSource(payload.getSource());
            labelInDb.setField(payload.getField());
            labelInDb.setOperation(payload.getOperation());
            labelInDb.setEnabled(payload.isEnabled());
            labelInDb.setExperimental(payload.isExperimental());
            labelRepository.save(labelInDb);
        } else {
            throw new IllegalArgumentException("label doesn't exist with id " + payload.getId());
        }

        return labelRepository.findAll();
    }

    @PostMapping("/api/label/add")
    public Iterable<Label> addLabel(@RequestBody AddLabelPayload payload) {
        Label labelInDb = new Label();
        labelInDb.setSource(payload.getSource());
        labelInDb.setField(payload.getField());
        labelInDb.setOperation(payload.getOperation());
        labelInDb.setEnabled(payload.isEnabled());
        labelInDb.setExperimental(payload.isExperimental());
        labelRepository.save(labelInDb);
        return labelRepository.findAll();
    }

    @PostMapping("/api/label/enable")
    public Iterable<Label> enableLabel(@RequestBody EnableLabelPayload payload) {
        Optional<Label> labelInDbOpt = labelRepository.findById(payload.getId());
        if (labelInDbOpt.isPresent()) {
            Label labelInDb = labelInDbOpt.get();
            labelInDb.setEnabled(payload.isEnabled());
            labelRepository.save(labelInDb);
        } else {
            throw new IllegalArgumentException("label doesn't exist with id " + payload.getId());
        }

        return labelRepository.findAll();
    }
}

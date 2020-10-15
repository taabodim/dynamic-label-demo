package com.mediamath.bidder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediamath.bidder.model.Label;
import com.mediamath.bidder.model.LabelRepository;
import com.mediamath.bidder.model.VideoPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

    @GetMapping("/label/list")
    public Iterable<Label> listLabels() {
        return labelRepository.findAll();
    }
}

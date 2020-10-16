package com.mediamath.delphi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediamath.bidder.DelphiRequest;
import com.mediamath.bidder.model.LabelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DelphiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DelphiController.class);
    
        private DelphiService delphiService;
        private ObjectMapper objectMapper;
        private LabelRepository labelRepository;

        @Autowired
        public DelphiController(
                ObjectMapper objectMapper,
                DelphiService delphiService,
                LabelRepository labelRepository) {
            this.objectMapper = objectMapper;
            this.delphiService = delphiService;
            this.labelRepository = labelRepository;
        }

        @PostMapping("/bidPrice")
        public void processBid(
                @RequestBody DelphiRequest payload) throws IOException {
            LOGGER.info("payload received : {}", payload);
            delphiService.getBidPrice(payload);
        }
}

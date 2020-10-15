package com.mediamath.adx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.mediamath.bidder.HttpClientService;
import com.mediamath.bidder.model.Device;
import com.mediamath.bidder.model.Site;
import com.mediamath.bidder.model.VideoPayload;
import com.mediamath.bidder.BidderMainConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdxExchange {
    private static final Logger LOGGER = LoggerFactory.getLogger(BidderMainConfig.class);
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ExecutorService executor = Executors.newCachedThreadPool();
    private final String videoSample;
    private final HttpClientService httpClientService;

    public AdxExchange(HttpClientService httpClientService) throws IOException {
        videoSample = FileUtils.readFileToString(new File("/etc/dynamic-label-demo/sample-request.txt"), Charset.defaultCharset());
        this.httpClientService = httpClientService;
    }

    @PostConstruct
    public void sendVideoRequests() {

        Executors.newCachedThreadPool().submit(() -> {
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//            LOGGER.info("sending bid request : {}", videoSample);
                try {
                    Random random = new Random();
                    VideoPayload vp = new VideoPayload();
                    vp.setSite(new Site());
                    vp.setDevice(new Device());
                    vp.getDevice().setId(RandomStringUtils.randomAlphabetic(16));
                    vp.getDevice().setLat(random.nextDouble());
                    vp.getDevice().setLon(random.nextDouble());
                    vp.getDevice().setCountry(random.nextBoolean() ? "US" : "UK");
                    vp.setBidfloor(random.nextDouble() % 20);
                    vp.setSecure(random.nextBoolean());
                    vp.getSite().setDomain(random.nextBoolean() ? "readersdigest.us" : "bbc.com");
                    vp.getSite().setCat(ImmutableList.of("IAB1, IAB2"));
                    vp.getSite().setPage(vp.getSite().getDomain() + "/womenhealth.asp");
                    String vpJson = OBJECT_MAPPER.writeValueAsString(vp);
                    LOGGER.info("sending bid request : {}", vpJson);
                    httpClientService.sendPostRequestWithJsonBody("/bid",
                            vpJson,
                            HttpClientService.PathOption.RELATIVE
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

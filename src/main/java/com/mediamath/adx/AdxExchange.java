package com.mediamath.adx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.mediamath.bidder.HttpClientService;
import com.mediamath.bidder.model.*;
import com.mediamath.bidder.BidderMainConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdxExchange {
    private static final Logger LOGGER = LoggerFactory.getLogger(BidderMainConfig.class);
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final ExecutorService pool;

    private ExecutorService executor = Executors.newCachedThreadPool();
    private final String videoSample;
    private final HttpClientService httpClientService;
    DecimalFormat df = new DecimalFormat("#.####");
    public AdxExchange(HttpClientService httpClientService) throws IOException {
        videoSample = FileUtils.readFileToString(new File("/etc/dynamic-label-demo/sample-request.txt"), Charset.defaultCharset());
        this.httpClientService = httpClientService;
        pool = Executors.newFixedThreadPool(10);
    }

    @PostConstruct
    public void sendVideoRequests() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pool.submit(() -> {

//            LOGGER.info("sending bid request : {}", videoSample);
                try {
                    List<String> domains = ImmutableList.of("readersdigest.us",
                            "bbc.com", "cnn.com", "fifa.com",
                            "nba.com", "fashiontv.com", "euronews.com", "hollywoodstar.com"
                    );
                    Random random = new Random();
                    VideoPayload vp = new VideoPayload();
                    vp.setImp(new Impression());
                    vp.setSite(new Site());
                    vp.setDevice(new Device());
                    vp.setId(RandomStringUtils.randomAlphabetic(16));
                    vp.getImp().setId(RandomStringUtils.randomAlphabetic(4) + "-" + RandomStringUtils.randomAlphabetic(8) + "-" + RandomStringUtils.randomAlphabetic(4));
                    vp.getImp().setId(vp.getImp().getId().toUpperCase());
                    vp.getImp().setVideo(new Video());

                    vp.getImp().getVideo().getProtocols().add(2);
                    vp.getImp().getVideo().getProtocols().add(4);
                    vp.getImp().getVideo().getProtocols().add(5);
                    vp.getImp().getVideo().setMaxduration(random.nextInt(100));
                    vp.getImp().getVideo().setMinduration(random.nextInt(vp.getImp().getVideo().getMaxduration()));
                    vp.getImp().getVideo().getMimes().add("video/mpg4");
                    vp.getImp().getVideo().getMimes().add("video/mp3");

                    vp.getDevice().setId(RandomStringUtils.randomAlphabetic(16));

                    vp.getDevice().setGeo(new Geo());

                    vp.getDevice().getGeo().setLat(Double.parseDouble(df.format(random.nextDouble() % 150)));
                    vp.getDevice().getGeo().setLon(Double.parseDouble(df.format(random.nextDouble() % 150)));
                    vp.getDevice().getGeo().setCountry(random.nextBoolean() ? "US" : "UK");
                    vp.getDevice().getGeo().setZipCode(random.nextInt(1000000));
                    vp.getDevice().getGeo().setUtcoffset(random.nextInt(10));
                    vp.getDevice().getGeo().setConnectiontype(random.nextInt(5));

                    vp.setBidfloor(Double.parseDouble(df.format(random.nextDouble() % 20)));
                    vp.setSecure(random.nextBoolean());
                    vp.getSite().setId(String.valueOf(random.nextInt(1000000)));
                    vp.getSite().setDomain(domains.get(random.nextInt(domains.size())));
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
            });
        }
    }
}

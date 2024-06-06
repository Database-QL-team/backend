package ggyuel.ggyuup.db2024PStogether.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PSTogetherResponseDTO {

    public static class PSTogetherDetailDTO {
        @JsonProperty
        private int togetherid;
        @JsonProperty
        private int pid;
        @JsonProperty
        private String togethertitle;
        @JsonProperty
        private String handle;
        @JsonProperty
        private String link;
        @JsonProperty
        private String pw;

        public PSTogetherDetailDTO(@JsonProperty("togetherid") int togetherid,
                                   @JsonProperty("pid") int pid,
                                   @JsonProperty("togethertitle") String togethertitle,
                                   @JsonProperty("handle") String handle,
                                   @JsonProperty("link") String link,
                                   @JsonProperty("pw") String pw) {
            this.togetherid = togetherid;
            this.pid = pid;
            this.togethertitle = togethertitle;
            this.handle = handle;
            this.link = link;
            this.pw = pw;
        }
    }

    public static class PSTogetherPreviewDTO {
        @JsonProperty
        int pid;
        @JsonProperty
        String togethertitle;
        @JsonProperty
        String handle;

        public PSTogetherPreviewDTO (@JsonProperty("pid") int pid,
                                     @JsonProperty("togethertitle") String togethertitle,
                                     @JsonProperty("handle") String handle) {
            this.pid = pid;
            this.togethertitle = togethertitle;
            this.handle = handle;
        }
    }

}

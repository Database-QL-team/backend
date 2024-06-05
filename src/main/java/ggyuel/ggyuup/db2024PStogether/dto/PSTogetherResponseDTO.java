package ggyuel.ggyuup.db2024PStogether.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PSTogetherResponseDTO {

    public static class PSTogetherDetailDTO {
        @JsonProperty
        private int pid;
        @JsonProperty
        private String title;
        @JsonProperty
        private String handle;
        @JsonProperty
        private String github_link;
        @JsonProperty
        private String pw;

        public PSTogetherDetailDTO(@JsonProperty("pid") int pid,
                                   @JsonProperty("title") String title,
                                   @JsonProperty("handle") String handle,
                                   @JsonProperty("github_link") String github_link,
                                   @JsonProperty("pw") String pw) {
            this.pid = pid;
            this.title = title;
            this.handle = handle;
            this.github_link = github_link;
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

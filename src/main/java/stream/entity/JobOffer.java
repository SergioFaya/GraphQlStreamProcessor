package stream.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class JobOffer {
    @JsonProperty("url_link")
    private String urlLink;
    @JsonProperty("has_salary")
    private boolean hasSalary;
    private Salary salary;
    private String description;
    private String company;
    @JsonProperty("company_logo_url")
    private String companyLogoUrl;
    private Location location;
    private String title;
    @JsonProperty("published_at")
    private String publishedAt;
    private boolean remote;
    private List<String> tags;
}
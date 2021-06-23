package stream.entity.output;

import lombok.Data;

import java.util.List;

@Data
public class GraphqlJob {
	private List<GraphqlCitiesItem> cities;
	private String applyUrl;
	private String description;
	private Company company;
	private String id;
	private String title;
	private List<GraphqlNameItem> tags;
	private Boolean isPublished;
	private String postedAt;
	private String createdAt;
	private String updatedAt;
}
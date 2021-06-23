package stream.entity.output;

import lombok.Data;

@Data
public class GraphqlCitiesItem {
	private GraphqlNameItem country;
	private String name;
}

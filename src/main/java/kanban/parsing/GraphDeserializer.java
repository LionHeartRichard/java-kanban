package kanban.parsing;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.type.MapType;

import kanban.util.Graph;

public class GraphDeserializer extends JsonDeserializer<Graph<?>> implements ContextualDeserializer {

	private JavaType type;

	public GraphDeserializer() {
	}

	public GraphDeserializer(JavaType type) {
		this.type = type;
	}

	@Override
	public Graph<?> deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		Graph<?> graph = new Graph<>();
		ObjectMapper mapper = (ObjectMapper) parser.getCodec();

		JavaType setType = mapper.getTypeFactory().constructCollectionType(Set.class, type);
		MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, type, setType);

		graph.setAdjacencyList(mapper.readValue(parser, mapType));

		return graph;
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty property) {
		JavaType type = property.getType().containedType(0);
		return new GraphDeserializer(type);
	}
}

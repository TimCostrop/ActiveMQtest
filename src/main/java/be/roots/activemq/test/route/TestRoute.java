package be.roots.activemq.test.route;

import be.roots.activemq.test.TestException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer:test?period=10000")
				.routeId("timerToLoadMessages")
				.transacted()
				.to("activemq://test.queue")
		;

		from("activemq://test.queue")
				.routeId("triggerExceptions")
				.transacted()
				.throwException(TestException.class, "DLQ test")
				.to("log:be.roots.activemq.test.route.TestRoute?level=INFO")
		;
	}
}

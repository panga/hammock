/*
 * Copyright 2017 Hammock and its contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ws.ament.hammock.brave;

import io.restassured.RestAssured;
import org.assertj.core.groups.Tuple;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import zipkin.Span;

import javax.inject.Inject;

import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class BraveIntegrationTest {
    @Inject
    private SpanReporter spanReporter;
    @Deployment
    public static Archive<?> archive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addClasses(BraveTracingFeatureProvider.class, BraveProducers.class, SpanReporter.class);
    }

    @Test
    public void shouldCreateSpan() {
        RestAssured.get("/simple");
        assertThat(spanReporter.getSpans()).hasSize(3);
        Long traceId = spanReporter.getSpans().get(0).traceId;
        assertThat(spanReporter.getSpans()).extracting((Function<Span, Long>) span -> span.traceId).containsExactly(new Tuple(traceId), new Tuple(traceId), new Tuple(traceId));
    }

}
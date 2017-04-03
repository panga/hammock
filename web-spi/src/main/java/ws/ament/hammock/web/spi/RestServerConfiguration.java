/*
 * Copyright 2016 Hammock and its contributors
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

package ws.ament.hammock.web.spi;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RestServerConfiguration {
    @Inject
    @ConfigProperty(name="rest.uri.path", defaultValue = "/")
    private String restUriPath;

    public String getRestServerUri() {
        return restUriPath;
    }

    public String getRestServletMapping() {
        String propertyValue = restUriPath;
        if(!propertyValue.endsWith("/")) {
            propertyValue += "/";
        }
        return propertyValue + "*";
    }
}
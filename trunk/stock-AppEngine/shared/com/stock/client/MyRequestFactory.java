/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.stock.client;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.ServiceName;
import com.stock.server.entities.Quote;
import com.stock.shared.MessageProxy;
import com.stock.shared.QuoteProxy;
import com.stock.shared.RegistrationInfoProxy;
import com.stock.shared.StockProxy;
import com.stock.shared.UserListProxy;


public interface MyRequestFactory extends RequestFactory {

  @ServiceName("com.stock.server.HelloWorldService")
  public interface HelloWorldRequest extends RequestContext {
    /**
     * Retrieve a "Hello, World" message from the server.
     */
    Request<String> getMessage();
  }

  @ServiceName("com.stock.server.RegistrationInfo")
  public interface RegistrationInfoRequest extends RequestContext {
    /**
     * Register a device for C2DM messages.
     */
    InstanceRequest<RegistrationInfoProxy, Void> register();

    /**
     * Unregister a device for C2DM messages.
     */
    InstanceRequest<RegistrationInfoProxy, Void> unregister();
  }

  @ServiceName("com.stock.server.Message")
  public interface MessageRequest extends RequestContext {
    /**
     * Send a message to a device using C2DM.
     */
    InstanceRequest<MessageProxy, String> send();
  }
  
  @ServiceName(value = "com.stock.server.StockDao", locator = "com.stock.server.DaoServiceLocator")
  public interface StockRequest extends RequestContext {
    Request<Void> save(StockProxy entity);
    Request<List<StockProxy>> getAll();
    Request<List<StockProxy>> getAllWithChildren();
    Request<List<QuoteProxy>> getChildren(StockProxy ss);
    Request<QuoteProxy> getChildren(long id);
    Request<List<QuoteProxy>> getChildrens(long id);
  }
  

  
  @ServiceName(value = "com.stock.server.UserListDao", locator = "com.stock.server.DaoServiceLocator")
  public interface UserListRequest extends RequestContext {
    Request<Void> save(UserListProxy entity);
    Request<List<UserListProxy>> getAll();
    Request<UserListProxy> get(Long id);
  }
  
  
  @ServiceName(value = "com.stock.server.QuoteDao", locator = "com.stock.server.DaoServiceLocator")
  public interface QuoteRequest extends RequestContext {
    Request<Void> save(QuoteProxy entity);
    Request<List<QuoteProxy>> getAll();
  }

  HelloWorldRequest helloWorldRequest();

  RegistrationInfoRequest registrationInfoRequest();

  MessageRequest messageRequest();
  
  StockRequest stockRequest();
  
  UserListRequest userListRequest();
  
  QuoteRequest quoteRequest();
}

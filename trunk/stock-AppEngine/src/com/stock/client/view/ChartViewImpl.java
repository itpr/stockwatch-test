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
package com.stock.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link ChartView}.
 */
public class ChartViewImpl extends Composite implements ChartView {

	interface Binder extends UiBinder<Widget, ChartViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;


	public ChartViewImpl() {
		initWidget(binder.createAndBindUi(this));
	}


	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}

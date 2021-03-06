/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2017 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.ui.repo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.pentaho.di.repository.IRepositoryService;

public class RepositoryServiceSessionTimeoutHandler implements InvocationHandler {

  private final IRepositoryService repositoryService;

  private final SessionTimeoutHandler sessionTimeoutHandler;

  public RepositoryServiceSessionTimeoutHandler( IRepositoryService repositoryService,
      SessionTimeoutHandler sessionTimeoutHandler ) {
    this.repositoryService = repositoryService;
    this.sessionTimeoutHandler = sessionTimeoutHandler;
  }

  @Override
  public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
    try {
      return method.invoke( repositoryService, args );
    } catch ( InvocationTargetException ex ) {
      return sessionTimeoutHandler.handle( repositoryService, ex.getCause(), method, args );
    }
  }
}

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.shrinkwrap.descriptor.impl.spec.servlet.web;

import org.jboss.shrinkwrap.descriptor.api.Node;
import org.jboss.shrinkwrap.descriptor.api.spec.servlet.web.InitParamDef;

/**
 * @author Dan Allen
 */
// TODO could be generic since servlet can use it too
public class InitParamDefImpl extends WebAppDescriptorImpl implements InitParamDef
{
   protected Node child;
   
   public InitParamDefImpl(Node webApp, Node child)
   {
      super(webApp);
      this.child = child;
   }
   
   /* (non-Javadoc)
    * @see org.jboss.shrinkwrap.descriptor.api.spec.web.FilterDef#initParam(java.lang.String, java.lang.Object)
    */
   @Override
   public InitParamDef initParam(String name, Object value)
   {
      Node init = child.create("init-param");
      init.create("param-name").text(name);
      init.create("param-value").text(String.valueOf(value));
      return this;
   }
}

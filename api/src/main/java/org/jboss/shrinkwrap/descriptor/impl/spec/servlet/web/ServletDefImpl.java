/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.shrinkwrap.descriptor.impl.spec.servlet.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.shrinkwrap.descriptor.api.Node;
import org.jboss.shrinkwrap.descriptor.api.spec.servlet.web.ServletDef;
import org.jboss.shrinkwrap.descriptor.api.spec.servlet.web.ServletMappingDef;
import org.jboss.shrinkwrap.descriptor.impl.base.Strings;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class ServletDefImpl extends WebAppDescriptorImpl implements ServletDef
{
   private final Node servlet;

   public ServletDefImpl(Node webApp, Node servlet)
   {
      super(webApp);
      this.servlet = servlet;
   }

   @Override
   public ServletDef name(String name)
   {
      servlet.getOrCreate("servlet-name").text(name);
      return this;
   }

   @Override
   public ServletDef asyncSupported(boolean value)
   {
      servlet.getOrCreate("async-supported").text(value);
      return this;
   }

   @Override
   public ServletDef initParam(String name, Object value)
   {
      InitParamDefImpl param = new InitParamDefImpl(getRootNode(), servlet);
      param.initParam(name, value == null ? null : value.toString());
      return this;
   }

   @Override
   public ServletDef loadOnStartup(int order)
   {
      servlet.getOrCreate("load-on-startup").text(order);
      return this;
   }

   @Override
   public ServletMappingDef mapping()
   {
      Node mappingNode = getRootNode().create("servlet-mapping");
      ServletMappingDef mapping = new ServletMappingDefImpl(getRootNode(), servlet, mappingNode);
      mapping.servletName(getName());
      return mapping;
   }

   @Override
   public ServletDef servletClass(Class<?> clazz)
   {
      return servletClass(clazz.getName());
   }

   @Override
   public ServletDef servletClass(String clazz)
   {
      servlet.getOrCreate("servlet-class").text(clazz);
      return this;
   }

   @Override
   public String getName()
   {
      return servlet.textValue("servlet-name");
   }

   @Override
   public String getInitParam(String name)
   {
      Map<String, String> params = getInitParams();
      for (Entry<String, String> e : params.entrySet())
      {
         if (e.getKey() != null && e.getKey().equals(name))
         {
            return e.getValue();
         }
      }
      return null;
   }

   @Override
   public Map<String, String> getInitParams()
   {
      Map<String, String> result = new HashMap<String, String>();
      List<Node> params = servlet.get("init-param");
      for (Node node : params)
      {
         result.put(node.textValue("param-name"), node.textValue("param-value"));
      }
      return result;
   }

   @Override
   public boolean isAsyncSupported()
   {
      return Strings.isTrue(servlet.textValue("async-supported"));
   }

   @Override
   public int getLoadOnStartup() throws NumberFormatException
   {
      String tex = servlet.textValue("load-on-startup");
      return tex == null ? null : Integer.valueOf(tex);
   }

   @Override
   public List<ServletMappingDef> getMappings()
   {
      List<ServletMappingDef> result = new ArrayList<ServletMappingDef>();
      List<ServletMappingDef> mappings = getServletMappings();
      for (ServletMappingDef mapping : mappings)
      {
         if (Strings.areEqualTrimmed(this.getName(), mapping.getServletName()))
         {
            result.add(mapping);
         }
      }
      return result;
   }

}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.hk2.xml.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This model is a description of the children and non-children nodes
 * of a Bean.  It contains only Strings or other easy to constructs
 * structures so that it can be added to the proxy at build time
 * and hence save some reflection at runtime
 * 
 * @author jwells
 *
 */
public class Model implements Serializable {
    private static final long serialVersionUID = 752816761552710497L;

    /** The interface from which the JAXB proxy was created, fully qualified */
    private String originalInterface;
    
    /** The JAXB proxy of the originalInterface, fully qualified */
    private String translatedClass;
    
    /** If this node can be a root, the xml tag of the root of the document */
    private String rootName;
    
    /** A map from the property name (not the xml tag) to the parented child node */
    private final Map<String, ParentedModel> childrenByName = new HashMap<String, ParentedModel>();
    
    /** A map from non-child property name to the default value */
    private final Map<String, ChildDataModel> nonChildProperty = new HashMap<String, ChildDataModel>();
    
    /** If this node has a key, this is the property name of the key */
    private String keyProperty;
    
    public Model() {
    }
    
    public Model(String originalInterface,
        String translatedClass) {
        this.originalInterface = originalInterface;
        this.translatedClass = translatedClass;
    }
    
    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
    
    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }
    
    public void addChild(String methodDecaptializedName,
            String xmlTag,
            ChildType childType,
            String givenDefault) {
        ParentedModel pm = new ParentedModel(xmlTag, childType, givenDefault);
        childrenByName.put(methodDecaptializedName, pm);
    }
    
    public void addNonChild(String xmlTag, String defaultValue, String childType) {
        nonChildProperty.put(xmlTag, new ChildDataModel(childType, defaultValue));
    }

    /**
     * @return the originalInterface
     */
    public String getOriginalInterface() {
        return originalInterface;
    }

    /**
     * @return the translatedClass
     */
    public String getTranslatedClass() {
        return translatedClass;
    }

    /**
     * @return the rootName
     */
    public String getRootName() {
        return rootName;
    }

    /**
     * @return the keyProperty
     */
    public String getKeyProperty() {
        return keyProperty;
    }
    
    public Map<String, ParentedModel> getChildrenByName() {
        return childrenByName;
    }
    
    public Map<String, ChildDataModel> getNonChildProperties() {
        return nonChildProperty;
    }

    public String toString() {
        return "Model(" + originalInterface + "," + translatedClass + "," + rootName + "," + keyProperty + ")";
    }
    
}
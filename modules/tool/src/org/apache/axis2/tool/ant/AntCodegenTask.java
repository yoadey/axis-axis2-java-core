/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis2.tool.ant;

import org.apache.axis2.util.URLProcessor;
import org.apache.axis2.wsdl.codegen.CodeGenerationEngine;
import org.apache.axis2.wsdl.codegen.CommandLineOption;
import org.apache.axis2.wsdl.codegen.CommandLineOptionConstants;
import org.apache.axis2.wsdl.codegen.CommandLineOptionParser;
import org.apache.axis2.wsdl.util.ConfigPropertyFileLoader;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

import java.util.HashMap;
import java.util.Map;

public class AntCodegenTask extends Task {

    private String WSDLFileName = null;
    private String output = ".";
    private String packageName = URLProcessor.DEFAULT_PACKAGE;
    private String language = ConfigPropertyFileLoader.getDefaultLanguage();
    private String databindingName=ConfigPropertyFileLoader.getDefaultDBFrameworkName();

    private boolean asyncOnly = false;
    private boolean syncOnly = false;
    private boolean serverSide = false;
    private boolean testcase = false;
    private boolean generateServiceXml = false;
    private boolean generateAllClasses = false;
    private boolean unwrapClasses = false;

    private Path classpath;




    /**
     * 
     */
    public AntCodegenTask() {
        super();
    }


    /**
     * Sets the classpath.
     * @return Returns Path.
     */
    public Path createClasspath() {
        if (classpath == null) {
            classpath = new Path(getProject());
        }
        return classpath.createPath();
    }

    /**
     * Fills the option map. This map is passed onto
     * the code generation API to generate the code.
     */
    private Map fillOptionMap() {
        Map optionMap = new HashMap();

        ////////////////////////////////////////////////////////////////
        //WSDL file name
        optionMap.put(
                CommandLineOptionConstants.WSDL_LOCATION_URI_OPTION,
                new CommandLineOption(
                        CommandLineOptionConstants.WSDL_LOCATION_URI_OPTION,
                        getStringArray(WSDLFileName)));
        //output location
        optionMap.put(
                CommandLineOptionConstants.OUTPUT_LOCATION_OPTION,
                new CommandLineOption(
                        CommandLineOptionConstants.OUTPUT_LOCATION_OPTION,
                        getStringArray(output)));
        //////////////////////////////////////////////////////////////////
        // Databinding type
        optionMap.put(
                CommandLineOptionConstants.DATA_BINDING_TYPE_OPTION,
                new CommandLineOption(
                        CommandLineOptionConstants.DATA_BINDING_TYPE_OPTION,
                        getStringArray(databindingName)));

        // Async only option - forcing to generate async methods only
        if (asyncOnly) {
            optionMap.put(
                    CommandLineOptionConstants.CODEGEN_ASYNC_ONLY_OPTION,
                    new CommandLineOption(
                            CommandLineOptionConstants.CODEGEN_ASYNC_ONLY_OPTION,
                            new String[0]));
        }
        // Sync only option - forcing to generate Sync methods only
        if (syncOnly) {
            optionMap.put(
                    CommandLineOptionConstants.CODEGEN_SYNC_ONLY_OPTION,
                    new CommandLineOption(
                            CommandLineOptionConstants.CODEGEN_SYNC_ONLY_OPTION,
                            new String[0]));
        }

        //Package
        optionMap.put(
                CommandLineOptionConstants.PACKAGE_OPTION,
                new CommandLineOption(
                        CommandLineOptionConstants.PACKAGE_OPTION,
                        getStringArray(packageName)));

        //stub language
        optionMap.put(
                CommandLineOptionConstants.STUB_LANGUAGE_OPTION,
                new CommandLineOption(
                        CommandLineOptionConstants.STUB_LANGUAGE_OPTION,
                        getStringArray(language)));


        //server side and generate services.xml options
        if (serverSide) {
            optionMap.put(
                    CommandLineOptionConstants.SERVER_SIDE_CODE_OPTION,
                    new CommandLineOption(
                            CommandLineOptionConstants.SERVER_SIDE_CODE_OPTION,
                            new String[0]));

            //services XML generation - effective only when specified as the server side
            if (generateServiceXml) {
                optionMap.put(
                        CommandLineOptionConstants
                                .GENERATE_SERVICE_DESCRIPTION_OPTION,
                        new CommandLineOption(
                                CommandLineOptionConstants
                                        .GENERATE_SERVICE_DESCRIPTION_OPTION,
                                new String[0]));
            }
            //generate all option - Only valid when generating serverside code
            if (generateAllClasses) {
                optionMap.put(
                        CommandLineOptionConstants.GENERATE_ALL_OPTION,
                        new CommandLineOption(
                                CommandLineOptionConstants.GENERATE_ALL_OPTION,
                                new String[0]));
            }

        }

        //generate the test case
        if (testcase) {
            optionMap.put(
                    CommandLineOptionConstants.GENERATE_TEST_CASE_OPTION,
                    new CommandLineOption(
                            CommandLineOptionConstants.GENERATE_TEST_CASE_OPTION,
                            new String[0]));
        }

        //Unwrap classes option - this determines whether the generated classes are inside the stub/MR
        //or gets generates as seperate classes
        if (unwrapClasses) {
            optionMap.put(
                    CommandLineOptionConstants.UNPACK_CLASSES_OPTION,
                    new CommandLineOption(
                            CommandLineOptionConstants.UNPACK_CLASSES_OPTION,
                            new String[0]));
        }


        return optionMap;
    }

    /**
     * Utility method to convert a string into a single item string[]
     * @param value
     * @return Returns String[].
     */
    private String[] getStringArray(String value) {
        String[] values = new String[1];
        values[0] = value;
        return values;
    }

    public void execute() throws BuildException {
        try {
            /**
             * This needs the ClassLoader we use to load the task have all the dependancies set, hope that
             * is ok for now
             *
             * todo look into this further!!!!!
             */


            AntClassLoader cl = new AntClassLoader(
                    null,
                    getProject(),
                    classpath,
                    false);

            Thread.currentThread().setContextClassLoader(cl);
            cl.addPathElement(output);

            Map commandLineOptions = this.fillOptionMap();
            CommandLineOptionParser parser =
                    new CommandLineOptionParser(commandLineOptions);
            new CodeGenerationEngine(parser).generate();
        } catch (Throwable e) {
            throw new BuildException(e);
        }

    }

    public void setWSDLFileName(String WSDLFileName) {
        this.WSDLFileName = WSDLFileName;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAsyncOnly(boolean asyncOnly) {
        this.asyncOnly = asyncOnly;
    }

    public void setSyncOnly(boolean syncOnly) {
        this.syncOnly = syncOnly;
    }

    public void setServerSide(boolean serverSide) {
        this.serverSide = serverSide;
    }

    public void setTestcase(boolean testcase) {
        this.testcase = testcase;
    }

    public void setGenerateServiceXml(boolean generateServiceXml) {
        this.generateServiceXml = generateServiceXml;
    }

//    public static void main(String[] args) {
//        AntCodegenTask task = new AntCodegenTask();
//        task.setWSDLFileName(
//                "modules/samples/test-resources/wsdl/compound2.wsdl");
//        task.setOutput("temp");
//        task.execute();
//    }

    /**
     * @return Returns Path.
     */
    public Path getClasspath() {
        return classpath;
    }

    /**
     * @param path
     */
    public void setClasspath(Path path) {
        classpath = path;
    }

    public String getDatabindingName() {
        return databindingName;
    }

    public void setDatabindingName(String databindingName) {
        this.databindingName = databindingName;
    }
}

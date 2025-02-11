/*
 * Arguments.java
 *
 * Copyright (c) 2002-2012 Alexei Drummond, Andrew Rambaut and Marc Suchard
 *
 * This file is part of BEAST.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * BEAST is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 *  BEAST is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BEAST; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package utils;

import java.util.StringTokenizer;

import exceptions.ArgumentException;

public class Arguments {

    public static final String ARGUMENT_CHARACTER = "-";

    public static class Option {

        public Option(String label, String description) {
            this.label = label;
            this.description = description;
        }

        String label;
        String description;
        boolean isAvailable = false;
    }

//  public static class RealOption extends Option {
//
//      public RealOption(String label, String description) {
//          super(label, description);
//      }
//
//      public RealOption(String label, double minValue, double maxValue, String description) {
//          super(label, description);
//          this.minValue = minValue;
//          this.maxValue = maxValue;
//      }
//
//      double minValue = Double.NEGATIVE_INFINITY;
//      double maxValue = Double.POSITIVE_INFINITY;
//
//      double value = 0;
//  }
//    public static class RealArrayOption extends RealOption {
//
//        // A count of -1 means any length
//        public RealArrayOption(String label, int count, String description) {
//            this(label, count, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, description);
//        }
//
//        public RealArrayOption(String label, int count, double minValue, double maxValue, String description) {
//            super(label, minValue, maxValue, description);
//            this.count = count;
//        }
//
//        private int count;
//        double[] values = null;
//    }
   
    public static class StringOption extends Option {
        /**
         * @param label       Option name:
         * @param tag         Descriptive name of option argument.
         *                    Example - tag "file-name" will show '-save <file-name>' in the usage.
         * @param description
         */
        public StringOption(String label, String tag, String description) {
            super(label, description);
            this.tag = tag;
        }

        public StringOption(String label, String[] options, boolean caseSensitive, String description) {
            super(label, description);
            this.options = options;
            this.caseSensitive = caseSensitive;
        }

        String[] options = null;
        String tag = null;
        boolean caseSensitive = false;

        String value = null;
    }
    
    public static class StringArrayOption extends StringOption {

		public StringArrayOption(String label, int count, String[] options, boolean caseSensitive, String description) {
			super(label, options, caseSensitive, description);
			this.count = count;
		}
    	  
    	public StringArrayOption(String label, int count, String tag, String description ){
    		super(label, tag, description);
    		this.count = count;
    	}
      
    	
    	
		int count;
		String[] values = null;
    	
    }//END: StringArrayOption
    
    public static class IntegerOption extends Option {

        public IntegerOption(String label, String description) {
            super(label, description);
        }

        public IntegerOption(String label, int minValue, int maxValue, String description) {
            super(label, description);
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        int minValue = Integer.MIN_VALUE;
        int maxValue = Integer.MAX_VALUE;

        int value = 0;
    }

    public static class IntegerArrayOption extends IntegerOption {

        public IntegerArrayOption(String label, String description) {
            this(label, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, description);
        }

        public IntegerArrayOption(String label, int count, String description) {
            this(label, count, Integer.MIN_VALUE, Integer.MAX_VALUE, description);
        }

        public IntegerArrayOption(String label, int minValue, int maxValue, String description) {
            this(label, 0, minValue, maxValue, description);
        }

        public IntegerArrayOption(String label, int count, int minValue, int maxValue, String description) {
            super(label, minValue, maxValue, description);
            this.count = count;
        }

        int count;

        int[] values = null;
    }

    public static class LongOption extends Option {

        public LongOption(String label, String description) {
            super(label, description);
        }

        public LongOption(String label, long minValue, long maxValue, String description) {
            super(label, description);
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        long minValue = Long.MIN_VALUE;
        long maxValue = Long.MAX_VALUE;

        long value = 0;
    }

    public static class RealOption extends Option {

        public RealOption(String label, String description) {
            super(label, description);
        }

        public RealOption(String label, double minValue, double maxValue, String description) {
            super(label, description);
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        double minValue = Double.NEGATIVE_INFINITY;
        double maxValue = Double.POSITIVE_INFINITY;

        double value = 0;
    }

    public static class RealArrayOption extends RealOption {

        //        public RealArrayOption(String label, String description) {
//            this(label, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, description);
//        }
        // A count of -1 means any length
        public RealArrayOption(String label, int count, String description) {
            this(label, count, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, description);
        }

//        public RealArrayOption(String label, double minValue, double maxValue, String description) {
//            this(label, 0, minValue, maxValue, description);
//        }

        public RealArrayOption(String label, int count, double minValue, double maxValue, String description) {
            super(label, minValue, maxValue, description);
            this.count = count;
        }

        private int count;

        double[] values = null;
    }

    /**
     * Parse a list of arguments ready for accessing
     */
    public Arguments(Option[] options) {
        this.options = options;
    }

    public Arguments(Option[] options, boolean caseSensitive) {
        this.options = options;
        this.caseSensitive = caseSensitive;
    }

    /**
     * Parse a list of arguments ready for accessing
     */
    public int parseArguments(String[] arguments) throws ArgumentException {

        int[] optionIndex = new int[arguments.length];
        for (int i = 0; i < optionIndex.length; i++) {
            optionIndex[i] = -1;
        }

        for (int i = 0; i < options.length; i++) {
            Option option = options[i];

            int index = findArgument(arguments, option.label);
            if (index != -1) {

                if (optionIndex[index] != -1) {
                    throw new ArgumentException("Argument, " + arguments[index] + " overlaps with another argument");
                }

                // the first value may be appended to the option label (e.g., '-t1.0'):
                String arg = arguments[index].substring(option.label.length() + 1);
                optionIndex[index] = i;
                option.isAvailable = true;

//                System.out.println(arg);
                
                if (option instanceof IntegerArrayOption) {

                    IntegerArrayOption o = (IntegerArrayOption) option;
                    o.values = new int[o.count];
                    int k = index;
                    int j = 0;

                    while (j < o.count) {
                        if (arg.length() > 0) {
                            StringTokenizer tokenizer = new StringTokenizer(arg, ",\t ");
                            while (tokenizer.hasMoreTokens()) {
                                String token = tokenizer.nextToken();
                                if (token.length() > 0) {
                                    try {
                                        o.values[j] = Integer.parseInt(token);
                                    } catch (NumberFormatException nfe) {
                                        throw new ArgumentException("Argument, " + arguments[index] +
                                                " has a bad integer value: " + token);
                                    }
                                    if (o.values[j] > o.maxValue || o.values[j] < o.minValue) {
                                        throw new ArgumentException("Argument, " + arguments[index] +
                                                " has a bad integer value: " + token);
                                    }
                                    j++;
                                }
                            }
                        }

                        k++;

                        if (j < o.count) {
                            if (k >= arguments.length) {
                                throw new ArgumentException("Argument, " + arguments[index] +
                                        " is missing one or more values: expecting " + o.count + " integers");
                            }

                            if (optionIndex[k] != -1) {
                                throw new ArgumentException("Argument, " + arguments[index] + " overlaps with another argument");
                            }

                            arg = arguments[k];
                            optionIndex[k] = i;
                        }
                    }
                } else if (option instanceof IntegerOption) {
                	
                    IntegerOption o = (IntegerOption) option;
                    if (arg.length() == 0) {
                        int k = index + 1;
                        if (k >= arguments.length) {
                            throw new ArgumentException("Argument, " + arguments[index] +
                                    " is missing its value: expecting an integer");
                        }

                        if (optionIndex[k] != -1) {
                            throw new ArgumentException("Argument, " + arguments[index] + " overlaps with another argument");
                        }
                        arg = arguments[k];
                        optionIndex[k] = i;
                    }

                    try {
                        o.value = Integer.parseInt(arg);
                    } catch (NumberFormatException nfe) {
                        throw new ArgumentException("Argument, " + arguments[index] +
                                " has a bad integer value: " + arg);
                    }
                    if (o.value > o.maxValue || o.value < o.minValue) {
                        throw new ArgumentException("Argument, " + arguments[index] +
                                " has a bad integer value: " + arg);
                    }
                } else if (option instanceof LongOption) {

                    LongOption o = (LongOption) option;
                    if (arg.length() == 0) {
                        int k = index + 1;
                        if (k >= arguments.length) {
                            throw new ArgumentException("Argument, " + arguments[index] +
                                    " is missing its value: expecting a long integer");
                        }

                        if (optionIndex[k] != -1) {
                            throw new ArgumentException("Argument, " + arguments[index] + " overlaps with another argument");
                        }
                        arg = arguments[k];
                        optionIndex[k] = i;
                    }

                    try {
                        o.value = Long.parseLong(arg);
                    } catch (NumberFormatException nfe) {
                        throw new ArgumentException("Argument, " + arguments[index] +
                                " has a bad integer value: " + arg);
                    }
                    if (o.value > o.maxValue || o.value < o.minValue) {
                        throw new ArgumentException("Argument, " + arguments[index] +
                                " has a bad long integer value: " + arg);
                    }
                } else if (option instanceof RealArrayOption) {
                    // I fixed only the real case to handle a variable sized array
                    // I don't have the time to figure out the right way, so I duplicated some code so
                    // that I do not break code by mistake
                    RealArrayOption o = (RealArrayOption) option;
                    if (o.count >= 0) {
                        final int count = o.count;
                        o.values = new double[count];
                        int k = index;
                        int j = 0;

                        while (j < count) {
                            if (arg.length() > 0) {
                                StringTokenizer tokenizer = new StringTokenizer(arg, ",\t ");
                                while (tokenizer.hasMoreTokens()) {
                                    String token = tokenizer.nextToken();
                                    if (token.length() > 0) {
                                        try {
                                            o.values[j] = Double.parseDouble(token);
                                        } catch (NumberFormatException nfe) {
                                            throw new ArgumentException("Argument, " + arguments[index] +
                                                    " has a bad real value: " + token);
                                        }
                                        if (o.values[j] > o.maxValue || o.values[j] < o.minValue) {
                                            throw new ArgumentException("Argument, " + arguments[index] +
                                                    " has a bad real value: " + token);
                                        }
                                        j++;
                                    }
                                }
                            }

                            k++;

                            if (j < count) {
                                if (k >= arguments.length) {
                                    throw new ArgumentException("Argument, " + arguments[index] +
                                            " is missing one or more values: expecting " + count + " integers");
                                }

                                if (optionIndex[k] != -1) {
                                    throw new ArgumentException("Argument, " + arguments[index] + " overlaps with another argument");
                                }

                                arg = arguments[k];
                                optionIndex[k] = i;
                            }
                        }
                    } else {

                        double[] values = new double[100];
                        index += 1;
                        arg = arguments[index];
                        optionIndex[index] = i;

                        int j = 0;

                        if (arg.length() > 0) {
                            StringTokenizer tokenizer = new StringTokenizer(arg, ",\t ");
                            while (tokenizer.hasMoreTokens()) {
                                String token = tokenizer.nextToken();
                                if (token.length() > 0) {
                                    try {
                                        values[j] = Double.parseDouble(token);
                                    } catch (NumberFormatException nfe) {
                                        throw new ArgumentException("Argument, " + arguments[index] +
                                                " has a bad real value: " + token);
                                    }
                                    if (values[j] > o.maxValue || values[j] < o.minValue) {
                                        throw new ArgumentException("Argument, " + arguments[index] +
                                                " has a bad real value: " + token);
                                    }
                                    j++;
                                }
                            }
                        }
                        o.values = new double[j];
                        System.arraycopy(values, 0, o.values, 0, j);
                    }//END: count check
                    
                } else if (option instanceof RealOption) {

                    RealOption o = (RealOption) option;
                    if (arg.length() == 0) {
                        int k = index + 1;
                        if (k >= arguments.length) {
                            throw new ArgumentException("Argument, " + arguments[index] +
                                    " is missing its value: expecting a real number");
                        }

                        if (optionIndex[k] != -1) {
                            throw new ArgumentException("Argument, " + arguments[index] + " overlaps with another argument");
                        }
                        arg = arguments[k];
                        optionIndex[k] = i;
                    }

                    try {
                        o.value = Double.parseDouble(arg);
                    } catch (NumberFormatException nfe) {
                        throw new ArgumentException("Argument, " + arguments[index] +
                                " has a bad real value: " + arg);
                    }
                    if (o.value > o.maxValue || o.value < o.minValue) {
                        throw new ArgumentException("Argument, " + arguments[index] +
                                " has a bad real value: " + arg);
                    }
                } else if(option instanceof StringArrayOption) {
                
                	// this should handle variable sized arrays (count = -1)
                	
                	StringArrayOption o = (StringArrayOption) option;
                	
                    String[] values = new String[100];
                    index += 1;
                    arg = arguments[index];
                    optionIndex[index] = i;

                    int j = 0;
                    if (arg.length() > 0) {
                        StringTokenizer tokenizer = new StringTokenizer(arg, ",\t ");
                        
                        while (tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            
                            if (token.length() > 0) {
                                    values[j] = token;
                                j++;
                            }
                        }
                    }
                    
                    o.values = new String[j];
                    System.arraycopy(values, 0, o.values, 0, j);
                	
                } else if (option instanceof StringOption) {
                	
                    StringOption o = (StringOption) option;
                    if (arg.length() == 0) {
                        int k = index + 1;
                        if (k >= arguments.length) {
                            throw new ArgumentException("Argument, " + arguments[index] +
                                    " is missing its value: expecting a string");
                        }

                        if (optionIndex[k] != -1) {
                            throw new ArgumentException("Argument, " + arguments[index] + " overlaps with another argument");
                        }
                        arg = arguments[k];
                        optionIndex[k] = i;
                    }

                    o.value = arg;

                    if (o.options != null) {
                        boolean found = false;
                        for (String option1 : o.options) {
                            if ((!caseSensitive && option1.equalsIgnoreCase(o.value)) || option1.equals(o.value)) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw new ArgumentException("Argument, " + arguments[index] +
                                    " has a bad string value: " + arg);
                        }
                    }
                    
                }  else { 

                	// is simply an Option - nothing to do...
                
                }
            }
        }//END: i loop

        int n = 0;
        int i = arguments.length - 1;
        while (i >= 0 && optionIndex[i] == -1 && !arguments[i].startsWith(ARGUMENT_CHARACTER)) {
            n++;
            i--;
        }
        leftoverArguments = new String[n];
        for (i = 0; i < n; i++) {
            leftoverArguments[i] = arguments[arguments.length - n + i];
        }

        for (i = 0; i < arguments.length - n; i++) {
            if (optionIndex[i] == -1) {
                throw new ArgumentException("Unrecognized argument: " + arguments[i]);
            }
        }
        return n;
    }

    private int findArgument(String[] arguments, String label) {
        for (int i = 0; i < arguments.length; i++) {

            if (arguments[i].length() - 1 >= label.length()) {
                if (arguments[i].startsWith(ARGUMENT_CHARACTER)) {
//					String l = arguments[i].substring(1, label.length() + 1);
//                                                String l = arguments[i];
                    String l = arguments[i].substring(1, arguments[i].length());
                    if ((!caseSensitive && label.equalsIgnoreCase(l)) || label.equals(l)) {
                        return i;
                    }

                }
            }
        }
        return -1;
    }

    /**
     * Does an argument with label exist?
     */
    public boolean hasOption(String label) {
        int n = findOption(label);
        if (n == -1) {
            return false;
        }

        return options[n].isAvailable;
    }

    /**
     * Return the value of an integer option
     */
    public int getIntegerOption(String label) {
        IntegerOption o = (IntegerOption) options[findOption(label)];
        return o.value;
    }

    /**
     * Return the value of an integer array option
     */
    public int[] getIntegerArrayOption(String label) {
        IntegerArrayOption o = (IntegerArrayOption) options[findOption(label)];
        return o.values;
    }

    /**
     * Return the value of an integer option
     */
    public long getLongOption(String label) {
        LongOption o = (LongOption) options[findOption(label)];
        return o.value;
    }

    /**
     * Return the value of an real number option
     */
    public double getRealOption(String label) {
        RealOption o = (RealOption) options[findOption(label)];
        return o.value;
    }

    /**
     * Return the value of an real array option
     */
    public double[] getRealArrayOption(String label) {
        RealArrayOption o = (RealArrayOption) options[findOption(label)];
        return o.values;
    }

    /**
     * Return the value of an string option
     */
    public String getStringOption(String label) {
        StringOption o = (StringOption) options[findOption(label)];
        return o.value;
    }

    public String[] getStringArrayOption(String label) {
    	StringArrayOption o = (StringArrayOption) options[findOption(label)];
    	return o.values;
    }
    
    /**
     * Return any arguments leftover after the options
     */
    public String[] getLeftoverArguments() {
        return leftoverArguments;
    }

    public void printUsage(String name, String commandLine) {

        System.out.print("  Usage: " + name);
        for (Option option : options) {
            System.out.print(" [-" + option.label);

            if (option instanceof IntegerArrayOption) {

                IntegerArrayOption o = (IntegerArrayOption) option;
                for (int j = 1; j <= o.count; j++) {
                    System.out.print(" <i" + j + ">");
                }
                System.out.print("]");
            } else if (option instanceof IntegerOption) {

                System.out.print(" <i>]");
            } else if (option instanceof RealArrayOption) {

                RealArrayOption o = (RealArrayOption) option;
                for (int j = 1; j <= o.count; j++) {
                    System.out.print(" <r" + j + ">");
                }
                System.out.print("]");
            } else if (option instanceof RealOption) {

                System.out.print(" <r>]");
            } else if (option instanceof StringOption) {

                StringOption o = (StringOption) option;
                if (o.options != null) {
                    System.out.print(" <" + o.options[0]);
                    for (int j = 1; j < o.options.length; j++) {
                        System.out.print("|" + o.options[j]);
                    }
                    System.out.print(">]");
                } else {
                    System.out.print(" <" + o.tag + ">]");
                }
            } else {
                System.out.print("]");
            }
        }
        System.out.println(" " + commandLine);

        for (Option option : options) {
            System.out.println("    -" + option.label + " " + option.description);
        }
    }

    private int findOption(String label) {
        for (int i = 0; i < options.length; i++) {
            String l = options[i].label;
            if ((!caseSensitive && label.equalsIgnoreCase(l)) || label.equals(l)) {
                return i;
            }
        }
        return -1;
    }

    private Option[] options = null;

    private String[] leftoverArguments = null;

    private boolean caseSensitive = false;
}


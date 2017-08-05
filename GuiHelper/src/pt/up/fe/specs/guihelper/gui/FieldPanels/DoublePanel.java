/*
 * Copyright 2010 SPeCS Research Group.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package pt.up.fe.specs.guihelper.gui.FieldPanels;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import pt.up.fe.specs.guihelper.FieldType;
import pt.up.fe.specs.guihelper.BaseTypes.FieldValue;
import pt.up.fe.specs.guihelper.BaseTypes.RawType;
import pt.up.fe.specs.guihelper.gui.FieldPanel;

/**
 *
 * @author Joao Bispo
 */
public class DoublePanel extends FieldPanel {

    private static final long serialVersionUID = 1L;

    public DoublePanel(String labelName) {
	label = new JLabel(labelName + " (double):");
	value = new JTextField(5);

	add(label);
	add(value);
	setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public void setText(String text) {
	value.setText(text);
    }

    public String getText() {
	return value.getText();
    }

    @Override
    public FieldType getType() {
	return FieldType.doublefloat;
    }

    @Override
    public FieldValue getOption() {
	String stringValue = getText();

	// Check if empty string
	if (stringValue.isEmpty()) {
	    return FieldValue.create(RawType.getEmptyValueDouble(), getType());
	}

	/*
	Double newValue = ParseUtils.parseDouble(stringValue);
	if (newValue == null) {
	   LoggingUtils.getLogger().
	           info("Could not parse '" + getText() + "' into a double.");
	   newValue = ParseUtils.parseDouble(RawType.EMPTY_VALUE_DOUBLE);
	}
	*/

	// return FieldValue.create(newValue, getType());
	return FieldValue.create(stringValue, getType());
    }

    @Override
    public void updatePanel(Object option) {
	setText(option.toString());
	// Double newDouble = (Double) option;
	// setText(newDouble.toString());
    }

    @Override
    public JLabel getLabel() {
	return label;
    }

    /**
     * INSTANCE VARIABLES
     */
    private JLabel label;
    private JTextField value;

}

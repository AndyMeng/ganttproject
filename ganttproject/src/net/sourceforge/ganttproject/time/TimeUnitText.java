/*
Copyright 2003-2012 Dmitry Barashev, GanttProject Team

This file is part of GanttProject, an opensource project management tool.

GanttProject is free software: you can redistribute it and/or modify 
it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

GanttProject is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with GanttProject.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.sourceforge.ganttproject.time;

import net.sourceforge.ganttproject.chart.GraphicPrimitiveContainer.Label;
import net.sourceforge.ganttproject.util.TextLengthCalculator;

public class TimeUnitText {
    private static final Label[] EMPTY_LABELS = new Label[] {
        new Label("", 0), new Label("", 0), new Label("", 0)
    };

    private String myLongText;

    private String myMediumText;

    private String myShortText;

    private Object myCalculatorState;

    private Label[] myLabels;

    public TimeUnitText(String longText, String mediumText, String shortText) {
        myLongText = longText;
        myMediumText = mediumText;
        myShortText = shortText;
    }

    public TimeUnitText(String mediumText) {
        myMediumText = mediumText;
        myLongText = mediumText;
        myShortText = mediumText;
    }

    public Label[] getLabels(int requestedMaxLength, TextLengthCalculator calculator) {
        if (!calculator.getState().equals(myCalculatorState)) {
            myCalculatorState = calculator.getState();
            myLabels = new Label[] {
                    new Label(myShortText, calculator.getTextLength(myShortText)),
                    new Label(myMediumText, calculator.getTextLength(myMediumText)),
                    new Label(myLongText, calculator.getTextLength(myLongText))
            };
        }
        int fitCount = getFitCount(myLabels, requestedMaxLength);
        if (fitCount == 0) {
            return EMPTY_LABELS;
        }
        Label[] result = new Label[fitCount];
        System.arraycopy(myLabels, 0, result, 0, fitCount);
        return result;
    }

    private int getFitCount(Label[] allLabels, int maxLength) {
        int count = 0;
        for (; count < allLabels.length; count++) {
            if (allLabels[count].lengthPx > maxLength) {
                break;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "long=" + myLongText + ", medium=" + myMediumText + ", short="
                + myShortText;
    }
}

package net.xytra.sylvarbo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.util.AbstractSelectModel;

import net.xytra.sylvarbo.enums.DisplayableEnum;

public class DisplayableSelectModel extends AbstractSelectModel {
    private List<OptionModel> optionModelList;

    public DisplayableSelectModel(DisplayableEnum[] items) {
        optionModelList = new ArrayList<OptionModel>(items.length);
        for (DisplayableEnum item: items) {
            optionModelList.add(new DisplayableOptionModel(item));
        }
    }

    @Override
    public List<OptionGroupModel> getOptionGroups() {
        return null;
    }

    @Override
    public List<OptionModel> getOptions() {
        return optionModelList;
    }

    public static class DisplayableOptionModel implements OptionModel {
        private DisplayableEnum displayable;

        private DisplayableOptionModel(DisplayableEnum displayable) {
            this.displayable = displayable;
        }

        @Override
        public String getLabel() {
            return displayable.getDisplayed();
        }

        @Override
        public boolean isDisabled() {
            return false;
        }

        @Override
        public Map<String, String> getAttributes() {
            return null;
        }

        @Override
        public Object getValue() {
            return displayable.toString();
        }
    }
}

package net.xytra.sylvarbo.base;

import net.xytra.common.cayenne.persistent.AbstractModifiable;

// page to edit a persisted object
public abstract class AbstractEditModifiablePage<T extends AbstractModifiable> extends AbstractEditPage<T> {
    @Override
    protected void onValidateFromEditForm() {
        if (editedObject.getUserCreated() == null) {
            editedObject.setCreatedNowBy(session.getUser());
        }

        editedObject.setModifiedNowBy(session.getUser());

        super.onValidateFromEditForm();
    }

}

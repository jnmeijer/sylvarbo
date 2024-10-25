package net.xytra.sylvarbo.pages;

import static net.xytra.common.tapestry.CommonTapestryConstants.NEW_OBJECT_ID;

import org.apache.cayenne.Cayenne;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.sylvarbo.base.AbstractListPage;
import net.xytra.sylvarbo.enums.NameStyle;
import net.xytra.sylvarbo.persistent.Person;

public class PersonList extends AbstractListPage<Person> {

    @Inject
    private PageRenderLinkSource linkSource;

    @Component
    private Form editForm;

    @Property
    private String currentError;

    @Property
    @Validate("required")
    private NameStyle style;

    protected Object onSuccess() {
        return linkSource.createPageRenderLinkWithContext(PersonIdentityAdd.class, NEW_OBJECT_ID, style, NEW_OBJECT_ID);
    }

    public void onActionFromDelete(int id) {
        System.err.println("--- onActionFromDelete: id="+id);
        Person object = Cayenne.objectForPK(context(), getObjectType(), id);
        if (object != null) {
            context().deleteObject(object);
            context().commitChanges();
            System.err.println("--- Success!");
        } else {
            System.err.println("--- No object found!");
        }
    }

    @Override
    protected Class<Person> getObjectType() {
        return Person.class;
    }

}

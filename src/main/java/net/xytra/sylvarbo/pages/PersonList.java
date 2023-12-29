package net.xytra.sylvarbo.pages;

import static net.xytra.common.tapestry.CommonTapestryConstants.NEW_OBJECT_ID;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import net.xytra.common.tapestry.session.Session;
import net.xytra.sylvarbo.base.AbstractListPage;
import net.xytra.sylvarbo.enums.NameStyle;
import net.xytra.sylvarbo.persistent.CayenneService;
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

    public PersonList() {
        this.session = new Session(CayenneService.getInstance().newObjectContext());
    }

    protected Object onSuccess() {
        return linkSource.createPageRenderLinkWithContext(NewPerson.class, style, NEW_OBJECT_ID);
    }

    @Override
    protected Class<Person> getObjectType() {
        return Person.class;
    }

}

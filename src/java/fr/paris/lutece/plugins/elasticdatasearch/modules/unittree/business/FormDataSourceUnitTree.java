package fr.paris.lutece.plugins.elasticdatasearch.modules.unittree.business;

import java.util.Collection;

import fr.paris.lutece.plugins.elasticdata.business.IDataSourceExternalAttributesProvider;
import fr.paris.lutece.plugins.elasticdata.modules.forms.business.FormsDataSource;

public class FormDataSourceUnitTree extends FormsDataSource {

	private final FormsDataSource _delegate;

    public FormDataSourceUnitTree( FormsDataSource delegate )
    {
        this._delegate = delegate;
    }

    /**
     * Set the external attributes provider for the data source
     * 
     * @param colExternalAttributesProvider
     */
    @Override
    public void setExternalAttributesProvider( Collection<IDataSourceExternalAttributesProvider> colExternalAttributesProvider )
    {
        _colExternalAttributesProvider = colExternalAttributesProvider;
    }

    
	
}

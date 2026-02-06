package fr.paris.lutece.plugins.elasticdatasearch.modules.unittree.web;

import java.util.List;
import java.util.Map;

import fr.paris.lutece.plugins.elasticdata.business.DataObject;
import fr.paris.lutece.plugins.elasticdata.business.IDataSourceExternalAttributesProvider;
import fr.paris.lutece.plugins.elasticdata.modules.forms.business.FormResponseDataObject;
import fr.paris.lutece.plugins.unittree.business.assignment.UnitAssignment;
import fr.paris.lutece.plugins.unittree.service.assignment.UnitAssignmentService;

public class DataSourceUnitTreeAttributesProvider implements IDataSourceExternalAttributesProvider {
	

	@Override
	public void provideAttributes(DataObject dataObject) {
		if ( dataObject != null && dataObject instanceof FormResponseDataObject )
       {
		FormResponseDataObject formResponseDataObject = (FormResponseDataObject) dataObject;
           provideIdentityAttributes( formResponseDataObject );
       }
		
	}

	@Override
	public void provideAttributes(List<DataObject> lstDataObjects) {
		for ( DataObject dataObject : lstDataObjects )
		{
			if ( dataObject != null && dataObject instanceof FormResponseDataObject )
		       {
				FormResponseDataObject formResponseDataObject = (FormResponseDataObject) dataObject;
		           provideIdentityAttributes( formResponseDataObject );
		       }
		}
		
	}
	
	/**
	    * Provide attributes from identitystore in a dataObject
	    * @param dataObject 
	    *              The data Object
	    */
	  private void provideIdentityAttributes( FormResponseDataObject dataObject )
	   {
		  UnitAssignment currentAssignment = UnitAssignmentService.findCurrentAssignment( dataObject.getFormResponseId(), "FORMS_FORM_RESPONSE" );
		  Map<String, Object> userResponses = dataObject.getUserResponses();
		  if ( currentAssignment != null && !userResponses.isEmpty() )
		  {
		      userResponses.put("Code UO", currentAssignment.getAssignedUnit().getCode() );
		      dataObject.setUserResponses( userResponses );
		  }
	   }

}

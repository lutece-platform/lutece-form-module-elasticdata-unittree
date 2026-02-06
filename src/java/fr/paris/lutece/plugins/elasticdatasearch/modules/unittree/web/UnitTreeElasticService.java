package fr.paris.lutece.plugins.elasticdatasearch.modules.unittree.web;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitService;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitUserService;
import fr.paris.lutece.plugins.unittree.service.unit.UnitService;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class UnitTreeElasticService {

	private static final String BEAN_UNIT_USER_SERVICE = "unittree.unitUserService";
	IUnitUserService _unitUserService = SpringContextService.getBean( BEAN_UNIT_USER_SERVICE );
	IUnitService _unitService = SpringContextService.getBean( UnitService.BEAN_UNIT_SERVICE );
	
	public boolean isUserInUnitTree( String strLibelleUnit, AdminUser user ) 
	{
		List<Unit> unitsByIdUser = _unitService.getUnitsByIdUser( Integer.valueOf( user.getUserId() ) , false);
        for (Unit unitUser : unitsByIdUser)
        {
        	if ( unitUser.getLabel().contains( strLibelleUnit ) )
        	{
        		return true;
        	}
        }
	
        return false;
        
	}
}

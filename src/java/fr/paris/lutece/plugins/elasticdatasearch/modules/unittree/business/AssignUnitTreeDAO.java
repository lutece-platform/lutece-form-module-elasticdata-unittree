/*
 * Copyright (c) 2002-2025, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */


package fr.paris.lutece.plugins.elasticdatasearch.modules.unittree.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * This class provides Data Access methods for AssignUnitTree objects
 */
public final class AssignUnitTreeDAO extends AbstractFilterDao implements IAssignUnitTreeDAO
{
    // Constants
    private static final String SQL_QUERY_INSERT = "INSERT INTO elasticdatasearch_unittree_assign_unit_tree ( name ) VALUES ( ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM elasticdatasearch_unittree_assign_unit_tree WHERE id_assign_unit_tree = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE elasticdatasearch_unittree_assign_unit_tree SET name = ? WHERE id_assign_unit_tree = ?";
   
	private static final String SQL_QUERY_SELECTALL = "SELECT id_assign_unit_tree, name FROM elasticdatasearch_unittree_assign_unit_tree";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_assign_unit_tree FROM elasticdatasearch_unittree_assign_unit_tree";

    private static final String SQL_QUERY_SELECTALL_BY_IDS = SQL_QUERY_SELECTALL + " WHERE id_assign_unit_tree IN (  ";
	private static final String SQL_QUERY_SELECT_BY_ID = SQL_QUERY_SELECTALL + " WHERE id_assign_unit_tree = ?";

	/**
     * Constructor
     */
	public AssignUnitTreeDAO( ) 
	{
		initMapSql( AssignUnitTree.class ); //Maps with name and type of each databases column associated to the business class attributes 
	}

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( AssignUnitTree assignUnitTree, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , assignUnitTree.getName( ) );
            
            daoUtil.executeUpdate( );
            
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                assignUnitTree.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<AssignUnitTree> load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeQuery( );
	        AssignUnitTree assignUnitTree = null;
	
	        if ( daoUtil.next( ) )
	        {
	            assignUnitTree = loadFromDaoUtil( daoUtil );
	        }
	
	        return Optional.ofNullable( assignUnitTree );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( AssignUnitTree assignUnitTree, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
	        int nIndex = 1;
	        
            	daoUtil.setString( nIndex++ , assignUnitTree.getName( ) );
	        daoUtil.setInt( nIndex , assignUnitTree.getId( ) );
	
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AssignUnitTree> selectAssigAssignUnitTreesnUnitTreesList( Plugin plugin )
    {
        List<AssignUnitTree> assignUnitTreeList = new ArrayList<>(  );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
				assignUnitTreeList.add( loadFromDaoUtil( daoUtil ) );
	        }
	
	        return assignUnitTreeList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdAssigAssignUnitTreesnUnitTreesList( Plugin plugin,  Map <String,String> mapFilterCriteria, String strColumnToOrder, String strSortMode )
    {
        List<Integer> assignUnitTreeList = new ArrayList<>( );
        
        String strSelectStatement = prepareSelectStatement( SQL_QUERY_SELECTALL_ID, mapFilterCriteria, strColumnToOrder, strSortMode );  
        
        try ( DAOUtil daoUtil = new DAOUtil( strSelectStatement, plugin ) )
        {
        
        	int nIndex = 1;
    	        
   	        for ( Map.Entry<String, String> filter : mapFilterCriteria.entrySet( ) ) 
   	        {
   	        	
   	        	if ( StringUtils.isNotBlank( filter.getValue( ) )  && _mapSql.containsKey( filter.getKey( ) ) ) 
   	        	{
   	        		daoUtil.setString( nIndex++ , filter.getValue( ) );
   	        	}
   	        }
    	        
	        daoUtil.executeQuery( );
	
	        while ( daoUtil.next( ) )
	        {
	        	assignUnitTreeList.add( daoUtil.getInt( 1 ) );
	        }
	
	        return assignUnitTreeList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectAssigAssignUnitTreesnUnitTreesReferenceList( Plugin plugin )
    {
        ReferenceList assignUnitTreeList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            assignUnitTreeList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
	        }
	
	        return assignUnitTreeList;
    	}
    }
    
    /**
     * {@inheritDoc }
     */
	@Override
	public List<AssignUnitTree> selectAssigAssignUnitTreesnUnitTreesListByIds( Plugin plugin, List<Integer> listIds ) 
	{
		List<AssignUnitTree> assignUnitTreeList = new ArrayList<>(  );
		
		StringBuilder builder = new StringBuilder( );

		if ( !listIds.isEmpty( ) )
		{
			for ( int i = 0 ; i < listIds.size(); i++ ) 
			{
			    builder.append( "?," );
			}
	
			String placeHolders = builder.deleteCharAt( builder.length( ) -1 ).toString( );
			String stmt = SQL_QUERY_SELECTALL_BY_IDS + placeHolders + ")";
			
	        try ( DAOUtil daoUtil = new DAOUtil( stmt, plugin ) )
	        {
	        	int index = 1;
				
				for ( Integer n : listIds ) 
				{
					daoUtil.setInt(  index++, n ); 
				}
	        	
	        	daoUtil.executeQuery(  );
	        	while ( daoUtil.next(  ) )
		        {
		            assignUnitTreeList.add( loadFromDaoUtil( daoUtil ) );
		        }
	        }
	    }
		return assignUnitTreeList;		
	}


	private AssignUnitTree loadFromDaoUtil (DAOUtil daoUtil) 
	{
		
		AssignUnitTree assignUnitTree = new AssignUnitTree( );
		int nIndex = 1;
		
		assignUnitTree.setId( daoUtil.getInt( nIndex++ ) );
		assignUnitTree.setName( daoUtil.getString( nIndex ) );
		
		return assignUnitTree;
	}
}

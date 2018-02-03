package nl.corwur.cytoscape.neo4j.internal;

import nl.corwur.cytoscape.neo4j.internal.exportneo4j.ExportNetworkConfiguration;
import nl.corwur.cytoscape.neo4j.internal.importneo4j.CypherQueryTemplate;
import nl.corwur.cytoscape.neo4j.internal.importneo4j.MappingStrategy;
import nl.corwur.cytoscape.neo4j.internal.graph.NodeLabel;
import nl.corwur.cytoscape.neo4j.internal.neo4j.CypherQuery;
import nl.corwur.cytoscape.neo4j.internal.task.exportnetwork.ExportNetworkToNeo4jTask;
import nl.corwur.cytoscape.neo4j.internal.task.importgraph.ImportGraphTask;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandFactoryTest {

    @Mock
    private Services services;

    @Test
    public void create() throws Exception {
        CommandFactory commandFactory = CommandFactory.create(services);
        assertNotNull("create command factory should not return null", commandFactory);
    }

    @Test
    public void createImportGraphTask() throws Exception {
        VisualMappingManager visualMappingManager = mock(VisualMappingManager.class);
        VisualStyle visualStyle = mock(VisualStyle.class);
        when(visualStyle.getTitle()).thenReturn("Default Style");
        when(visualMappingManager.getDefaultVisualStyle()).thenReturn(visualStyle);
        when(services.getVisualMappingManager()).thenReturn(visualMappingManager);
        CommandFactory commandFactory = CommandFactory.create(services);
        ImportGraphTask task = commandFactory.createImportGraphTask();
        assertNotNull("create import graph should not return null",task);
    }

    @Test
    public void createExportNetworkToNeo4jTask() throws Exception {
        CommandFactory commandFactory = CommandFactory.create(services);
        ExportNetworkConfiguration exportNetworkConfiguration = mock(ExportNetworkConfiguration.class);
        ExportNetworkToNeo4jTask task = commandFactory.createExportNetworkToNeo4jTask(exportNetworkConfiguration);
        assertNotNull("create export network to Neo4j should not return null",task);
    }

    @Test
    public void createRetrieveDataFromQueryTemplateTask() throws Exception {
        CommandFactory commandFactory = CommandFactory.create(services);
        CypherQueryTemplate query = mock(CypherQueryTemplate.class);
        MappingStrategy mappingStrategy = mock(MappingStrategy.class);
        when(query.getMapping()).thenReturn(mappingStrategy);
        ImportGraphTask task = commandFactory.createRetrieveDataFromQueryTemplateTask("Networkname", query, "visualStyle");
        assertNotNull("create retrieve data from query-template should not return null",task);
        verify(mappingStrategy).accept(any());
    }

    @Test
    public void createExecuteCypherQueryTask() throws Exception {
        CommandFactory commandFactory = CommandFactory.create(services);
        CypherQuery query = mock(CypherQuery.class);
        ImportGraphTask task = commandFactory.createExecuteCypherQueryTask("Networkname", query, "visualStyle");
        assertNotNull("create execute cypher-query should not return null",task);
    }
}
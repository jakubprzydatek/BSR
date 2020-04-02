package gui;

import model.Operation;
import utils.Util;
import webreport.html.OperationReportGenerator;
import webreport.html.OperationsStatistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;
import static utils.Constants.*;
import static utils.Util.showError;
import static utils.Util.showInformation;


public class GenerateReportActionListener
    implements ActionListener
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    private Set<Operation> allOperations;
    private Set<String> sources;


    public GenerateReportActionListener( Set<Operation> allOperations, Set<String> sources )
    {
        this.allOperations = allOperations;
        this.sources = sources;
    }


    @Override
    public void actionPerformed( ActionEvent e )
    {
        LOGGER.info( "Report saving started" );
        if( allOperations.isEmpty() || sources.isEmpty() )
        {
            LOGGER.warning( "There is no data to save" );
            showError( "There is no data to save" );
            return;
        }

        OperationsStatistics operationsStatistics =
            new OperationsStatistics( new ArrayList<>( allOperations ) );

        OperationReportGenerator reportGenerator =
            new OperationReportGenerator( new ArrayList<>( allOperations ),
                new ArrayList<>( sources ), operationsStatistics );

        String report = reportGenerator.generateHtml();

        Path path = Paths.get( CONFIGURATION_PATH, REPORT + LocalDate.now() + HTML_EXTENSION );

        try
        {
            Util.writeHtmlReport( report, path );
            showInformation( "Report has been saved in:\n" + path );
            LOGGER.info( "Report has been saved in:" + path );
        }
        catch( IOException ex )
        {
            LOGGER.warning( "Cannot write HTML report" );
            showError( "Cannot write HTML report" );
        }

    }
}

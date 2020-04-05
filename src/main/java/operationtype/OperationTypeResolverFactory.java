package operationtype;

import javax.swing.*;
import java.util.Optional;
import java.util.StringJoiner;

import static app.Log.LOGGER;
import static utils.Constants.PKO;


public class OperationTypeResolverFactory
{
    public Optional<OperationTypeResolver> match( String chosenBank )
    {
        if( PKO.equals( chosenBank ) )
        {
            return Optional.of( new PKOOperationTypeResolver() );
        }
        reportError( getErrorMessage( chosenBank ) );
        return Optional.empty();
    }


    private String getErrorMessage( String chosenBank )
    {
        return new StringJoiner( " " ).add( "Cannot match operation type resolver for" )
            .add( chosenBank ).toString();
    }


    private void reportError( String errorMessage )
    {
        LOGGER.warning( errorMessage );
        JOptionPane.showMessageDialog( null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE );
    }
}

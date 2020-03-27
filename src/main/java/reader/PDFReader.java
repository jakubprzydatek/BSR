package reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;
import static java.util.logging.Logger.getLogger;


public class PDFReader
{
    private final static Logger LOGGER = getLogger( GLOBAL_LOGGER_NAME );

    public static Optional<String> read( String path )
    {
        try
        {
            PDDocument document = PDDocument.load( new File( path ) );
            if( !document.isEncrypted() )
            {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition( true );

                PDFTextStripper tStripper = new PDFTextStripper();
                String stripperText = tStripper.getText( document );
                document.close();
                return Optional.of( stripperText );
            }
        }catch( IOException e ){
            LOGGER.warning( "PDF cannot be read" );
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

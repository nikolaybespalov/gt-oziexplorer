package com.github.nikolaybespalov.gtozi;

import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.imageio.GeoToolsWriteParams;
import org.geotools.factory.GeoTools;
import org.geotools.factory.Hints;
import org.opengis.coverage.grid.Format;
import org.opengis.coverage.grid.GridCoverageWriter;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.geotools.util.logging.Logging.getLogger;

@SuppressWarnings("WeakerAccess")
public class OziMapFormat extends AbstractGridFormat implements Format {
    private static final Logger LOGGER = getLogger(OziMapFormat.class);

    public OziMapFormat() {
        writeParameters = null;
        mInfo = new HashMap<>();
        mInfo.put("name", "Ozi");
        mInfo.put("description", "OziExplorer Map File Format");
        mInfo.put("vendor", "nikolaybespalov");
        mInfo.put("version", "0.1");
        mInfo.put("docURL", "https://github.com/nikolaybespalov/gt-ozi");
    }

    @Override
    public boolean accepts(Object source, Hints hints) {
        if (source == null) {
            LOGGER.severe("input should not be null");
            return false;
        }

        try {
            AbstractGridCoverage2DReader reader = getReader(source, hints);

            if (reader != null) {
                reader.dispose();

                return true;
            }
        } catch (Throwable t) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, t.getLocalizedMessage(), t);
            }
        }

        return false;
    }

    @Override
    public AbstractGridCoverage2DReader getReader(Object o) {
        return getReader(o, GeoTools.getDefaultHints());
    }

    @Override
    public AbstractGridCoverage2DReader getReader(Object source, Hints hints) {
        if (source == null) {
            LOGGER.severe("input should not be null");
            return null;
        }

        try {
            return new OziMapReader(source);
        } catch (IOException | FactoryException | TransformException e) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }

            return null;
        }
    }

    @Override
    public GridCoverageWriter getWriter(Object o) {
        return getWriter(o, GeoTools.getDefaultHints());
    }

    @Override
    public GridCoverageWriter getWriter(Object o, Hints hints) {
        return null;
    }

    @Override
    public GeoToolsWriteParams getDefaultImageIOWriteParameters() {
        return null;
    }
}

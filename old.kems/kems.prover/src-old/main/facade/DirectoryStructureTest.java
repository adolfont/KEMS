/*
 * Created on 15/03/2005
 *
 */
package facade;

import junit.framework.TestCase;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class DirectoryStructureTest extends TestCase {
    public void testNew() {

        String base = System.getProperty("user.home") + DirectoryStructure.FS
                + "TableauProver";

//        DirectoryStructure ds = new DirectoryStructure(base);
        DirectoryStructure ds = new DirectoryStructure();

        assertEquals(base + DirectoryStructure.FS + DirectoryStructure.LIB
                + DirectoryStructure.FS, ds
                .getCompletePath(DirectoryStructure.LIB));

        assertEquals(ds.getCompletePath(DirectoryStructure.LIB)
                + DirectoryStructure.EXT + DirectoryStructure.FS,
                ds.getCompletePath(DirectoryStructure.LIB,
                        DirectoryStructure.EXT));

        assertEquals(ds.getCompletePath(DirectoryStructure.LIB)
                + DirectoryStructure.GENERATED + DirectoryStructure.FS, ds
                .getCompletePath(DirectoryStructure.LIB,
                        DirectoryStructure.GENERATED));

        assertEquals(ds.getCompletePath(DirectoryStructure.PROBLEMS)
                + DirectoryStructure.CREATED + DirectoryStructure.FS
                + "wagnerNew" + DirectoryStructure.FS, ds.getCompletePath(
                DirectoryStructure.PROBLEMS, DirectoryStructure.CREATED,
                "wagnerNew"));

        assertEquals(ds.getCompletePath(DirectoryStructure.PROBLEMS)
                + DirectoryStructure.CREATED + DirectoryStructure.FS
                + "wagnerNew" + DirectoryStructure.FS + "gamma"
                + DirectoryStructure.FS, ds.getCompletePath(
                DirectoryStructure.PROBLEMS, DirectoryStructure.CREATED,
                "wagnerNew", "gamma"));

    }

}
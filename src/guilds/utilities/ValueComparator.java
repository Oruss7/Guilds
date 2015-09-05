package guilds.utilities;

import guilds.*;
import java.util.*;

public class ValueComparator implements Comparator<Guild>
{
    Map<Guild, Double> base;
    
    public ValueComparator(final Map<Guild, Double> base) {
        this.base = base;
    }
    
    @Override
    public int compare(final Guild a, final Guild b) {
        if (this.base.get(a) >= this.base.get(b)) {
            return -1;
        }
        return 1;
    }
}

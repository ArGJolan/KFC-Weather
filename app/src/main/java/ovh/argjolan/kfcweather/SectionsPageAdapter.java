package ovh.argjolan.kfcweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ArGJolan on 28/07/2017.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();

    /**
     * @param fragment : the fragment that will be added to the list
     */
    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * @param position : position of the selected tab
     * @return the unit the temperature will be show into as page title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getArguments().getString("unit");
    }

    /**
     * @param position : position of the selected tab
     * @return the instance of the fragment corresponding to the selected tab
     */
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * @return the number of fragments
     */
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

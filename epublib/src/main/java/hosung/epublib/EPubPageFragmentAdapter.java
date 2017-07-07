package hosung.epublib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import hosung.epublib.smil.TextElement;
import nl.siegmann.epublib.domain.SpineReference;

/**
 * Created by mahavir on 4/2/16.
 * Modifed by Hosung, Lee on 6/20/17
 */
public class EPubPageFragmentAdapter extends FragmentStatePagerAdapter {
    private final String mBookTitle;
    private List<SpineReference> mSpineReferences;
    private String mEpubFileName;
    private EPubPageFragment mEPubPageFragment;
    private ArrayList<TextElement> mTextElementArrayList;
    private boolean mIsSmileAvailable;

    public EPubPageFragmentAdapter(FragmentManager fm, List<SpineReference> spineReferences,
                                   String bookTitle, String epubFilename) {
        super(fm);
        this.mSpineReferences = spineReferences;
        this.mBookTitle = bookTitle;
        this.mEpubFileName = epubFilename;

        EPubReaderActivity.BUS.register(this);
    }

    @Override
    public Fragment getItem(int position) {
        mEPubPageFragment = EPubPageFragment.newInstance(position, mBookTitle, mEpubFileName, mTextElementArrayList, mIsSmileAvailable);
        mEPubPageFragment.setFragmentPos(position);
        return mEPubPageFragment;
    }

    @Override
    public int getCount() {
        return mSpineReferences.size();
    }

    @Subscribe
    public void setTextElementList(ArrayList<TextElement> textElementList) {
        if (textElementList != null && textElementList.size() > 0) {
            mIsSmileAvailable = true;
            mTextElementArrayList = textElementList;
        }
    }
}

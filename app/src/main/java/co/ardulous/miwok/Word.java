package co.ardulous.miwok;

/**
 * Created by ardulous on 28/8/17.
 */

public class Word {
    private String mmiwokTranslation;
    private String mdefaultTranslation;
    private int resId=-1;
    private int musicResource=-1;
    public Word(String miwokTranslation,String defaultTranslation,int mresource)
    {
        mmiwokTranslation=miwokTranslation;
        mdefaultTranslation=defaultTranslation;
        musicResource=mresource;
    }
    public Word(String miwokTranslation,String defaultTranslation,int resource,int mresource)
    {
        mmiwokTranslation=miwokTranslation;
        mdefaultTranslation=defaultTranslation;
        resId=resource;
        musicResource=mresource;
    }
    public String getmiwok()
    {
        return mmiwokTranslation;
    }
    public String getDefault()
    {
        return mdefaultTranslation;
    }
    public int getImage()
    {
        return resId;
    }
    public boolean hasImage()
    {
        if(resId==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public int getMusic()
    {
        return musicResource;
    }
}

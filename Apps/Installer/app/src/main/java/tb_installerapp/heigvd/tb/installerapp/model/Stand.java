package tb_installerapp.heigvd.tb.installerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anthony on 26.12.2015.
 */
public class Stand implements Parcelable{
    public String standKey;
    public String StandName;
    public String proprietaire;
    public int posX;
    public int posY;
    public String infoKey;

    public Stand(String standKey, String standName, String proprietaire, int posX, int posY, String infoKey) {
        this.standKey = standKey;
        StandName = standName;
        this.proprietaire = proprietaire;
        this.posX = posX;
        this.posY = posY;
        this.infoKey =infoKey;
    }

    public Stand(Parcel in) {
        standKey = in.readString();
        StandName = in.readString();
        proprietaire = in.readString();
        posX = in.readInt();
        posY = in.readInt();
        infoKey = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(standKey);
        dest.writeString(StandName);
        dest.writeString(proprietaire);
        dest.writeInt(posX);
        dest.writeInt(posY);
        dest.writeString(infoKey);
    }

    public static final Creator<Stand> CREATOR = new Creator<Stand>() {
        @Override
        public Stand createFromParcel(Parcel in) {
            return new Stand(in);
        }

        @Override
        public Stand[] newArray(int size) {
            return new Stand[size];
        }
    };


}

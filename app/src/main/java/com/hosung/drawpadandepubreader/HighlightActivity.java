package com.hosung.drawpadandepubreader;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hosung.drawpadandepubreader.models.EPubHighLight;

import java.util.ArrayList;

import hosung.epublib.EPubReaderActivity;
import hosung.epublib.UnderlinedTextView;
import hosung.epublib.model.Highlight;
import hosung.epublib.util.AppUtil;
import io.realm.Realm;
import io.realm.RealmResults;

public class HighlightActivity extends AppCompatActivity {
    private String mBookId;
    private ArrayList<Highlight> highlights = new ArrayList<>();
    private RecyclerView listHighlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_highlight);

        listHighlight=(RecyclerView)findViewById(R.id.listHighlight);
        mBookId = getIntent().getStringExtra("BookID");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        listHighlight.setLayoutManager(llm);
        listHighlight.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    private void initializeData(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<EPubHighLight> results
                = realm.where(EPubHighLight.class).equalTo("bookId",mBookId).findAll();
        if(results != null && results.size() > 0) {
            for (EPubHighLight ePubHighLight : results) {
                Highlight highlight = new Highlight();
                highlight.setId(ePubHighLight.getId());
                highlight.setBookId(ePubHighLight.getBookId());
                highlight.setContent(ePubHighLight.getContent());
                highlight.setContentPost(ePubHighLight.getContentPost());
                highlight.setContentPre(ePubHighLight.getContentPre());
                highlight.setDate(MyEPubReaderActivity.getDateTime(ePubHighLight.getDate()));
                highlight.setHighlightId(ePubHighLight.getHighlightId());
                highlight.setPage(ePubHighLight.getPage());
                highlight.setType(ePubHighLight.getType());
                if(ePubHighLight.getCurrentPagerPostion()!=null)
                    highlight.setCurrentPagerPostion(ePubHighLight.getCurrentPagerPostion());
                else
                    highlight.setCurrentPagerPostion(0);
                if(ePubHighLight.getCurrentWebviewScrollPos()!=null)
                    highlight.setCurrentWebviewScrollPos(ePubHighLight.getCurrentWebviewScrollPos());
                else
                    highlight.setCurrentWebviewScrollPos(0);
                highlight.setNote(ePubHighLight.getNote());

                highlights.add(highlight);
            }
        }
    }

    private void initializeAdapter(){
        HighlightAdapter adapter = new HighlightAdapter(highlights);
        listHighlight.setAdapter(adapter);
    }

    class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.HightlightViewHolder> {
        ArrayList<Highlight> highlights;
        HighlightAdapter(ArrayList<Highlight> highlights){
            this.highlights = highlights;
        }

        public class HightlightViewHolder extends RecyclerView.ViewHolder {
            LinearLayout highlightItem;
            TextView txt_hightlight_time;
            UnderlinedTextView txt_hightlight_text;
            TextView txt_hightlight_note;
            ImageView editnoteImg;
            ImageView deleteImg;

            public HightlightViewHolder(View view) {
                super(view);

                highlightItem = (LinearLayout) view.findViewById(R.id.highlightItem);

                txt_hightlight_time = (TextView) view.findViewById(R.id.txt_hightlight_time);
                txt_hightlight_text = (UnderlinedTextView) view.findViewById(R.id.txt_hightlight_text);
                txt_hightlight_note = (TextView) view.findViewById(R.id.txt_hightlight_note);

                deleteImg = (ImageView) view.findViewById(R.id.deleteImg);
                editnoteImg = (ImageView) view.findViewById(R.id.editnoteImg);

                highlightItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = (int) view.getTag();
                        Highlight highlight = highlights.get(i);
                        Intent intent = new Intent();
                        intent.putExtra("highlight_selected", (Parcelable) highlight);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public HightlightViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hightlight_item, viewGroup, false);
            HightlightViewHolder highlightVH = new HightlightViewHolder(v);
            return highlightVH;
        }

        @Override
        public void onBindViewHolder(HightlightViewHolder hightlightViewHolder, final int i) {
            hightlightViewHolder.highlightItem.setTag(i);

            hightlightViewHolder.txt_hightlight_text.setText(highlights.get(i).getContent());
            hightlightViewHolder.txt_hightlight_time.setText(AppUtil.formatDate(highlights.get(i).getDate()));
            hightlightViewHolder.txt_hightlight_note.setText(highlights.get(i).getNote());

            final Highlight curHightlight = highlights.get(i);
            hightlightViewHolder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Realm realm = Realm.getDefaultInstance();
                    EPubHighLight ePubHighLight
                            = realm.where(EPubHighLight.class).equalTo("id",curHightlight.getId()).findFirst();
                    realm.beginTransaction();
                    ePubHighLight.deleteFromRealm();
                    realm.commitTransaction();

                    highlights.remove(i);
                    notifyItemRemoved(i);
                }
            });
            hightlightViewHolder.editnoteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditNoteDailog(curHightlight);
                }
            });

        }

        private void showEditNoteDailog(final Highlight highlight) {
            final Dialog dailog = new Dialog(HighlightActivity.this, R.style.DialogCustomTheme);
            dailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dailog.setContentView(R.layout.dialog_edit_notes);
            dailog.show();
            String noteText = highlight.getNote();
            ((EditText) dailog.findViewById(R.id.edit_note)).setText(noteText);

            dailog.findViewById(R.id.btn_save_note).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String note =
                            ((EditText) dailog.findViewById(R.id.edit_note)).getText().toString();
                    if (note != null && (!TextUtils.isEmpty(note))) {
                        highlight.setNote(note);

                        Realm realm = Realm.getDefaultInstance();
                        EPubHighLight ePubHighLight
                                = realm.where(EPubHighLight.class).equalTo("id", highlight.getId()).findFirst();
                        realm.beginTransaction();
                        ePubHighLight.setNote(highlight.getNote());
                        realm.commitTransaction();

                        notifyDataSetChanged();
                        dailog.dismiss();
                    } else {
                        Toast.makeText(HighlightActivity.this,
                                getString(R.string.please_enter_note),
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return highlights.size();
        }
    }

}

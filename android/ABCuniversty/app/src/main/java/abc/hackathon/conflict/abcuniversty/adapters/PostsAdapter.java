package abc.hackathon.conflict.abcuniversty.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import abc.hackathon.conflict.abcuniversty.R;
import abc.hackathon.conflict.abcuniversty.models.Post;


/**
 * Created by mamoun on 13/07/18.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostClassViewHolder> {

    private Context mCtx;
    private int lastPosition = -1;
    private List<Post> postList;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public PostsAdapter(Context mCtx, List<Post> postList){
        this.mCtx=mCtx;
        this.postList = postList;
    }

    public interface Listener{

        void onCustomClick(int pos);
    }

    @Override
    public PostsAdapter.PostClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.post_layout, null);
        return new PostClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PostsAdapter.PostClassViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.StudentName.setText(post.getStudentName());
        holder.PostTitle.setText(post.getPostTitle());
        holder.PostDate.setText(post.getPostingDate());
        holder.PostContent.setText(post.getPostContent());

        Glide.with(mCtx)
                .load(post.getMediaLink())
                .placeholder(R.drawable.reading)
                .into(holder.ImageLink);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCustomClick(holder.getAdapterPosition());
            }
        });

        setAnimation(holder.itemView, position);


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostClassViewHolder extends RecyclerView.ViewHolder{
        TextView StudentName,PostTitle,PostDate,PostContent;
        ImageView ImageLink;
        View v;
        public PostClassViewHolder(View itemview){
            super(itemview);
            v = itemview;
            StudentName=(TextView) itemview.findViewById(R.id.SName);
            PostTitle=(TextView) itemview.findViewById(R.id.Ptitle);
            PostDate=(TextView) itemview.findViewById(R.id.PDate);
            PostContent=(TextView) itemview.findViewById(R.id.Pcontent);
            ImageLink=(ImageView) itemview.findViewById(R.id.Pimage);




        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mCtx, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}

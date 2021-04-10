package info.firozansari.presentation.view.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView

/**
 * Adaptar that manages a collection of [UserModel].
 */
class UsersAdapter @Inject internal constructor(context: Context?) :
    RecyclerView.Adapter<UserViewHolder?>() {
    interface OnItemClickListener {
        open fun onUserItemClicked(userModel: UserModel?)
    }

    private var usersCollection: MutableList<UserModel?>?
    private val layoutInflater: LayoutInflater?
    private var onItemClickListener: OnItemClickListener? = null
    override fun getItemCount(): Int {
        return if (usersCollection != null) usersCollection.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder? {
        val view: View = layoutInflater.inflate(R.layout.row_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        val userModel: UserModel? = usersCollection.get(position)
        holder.textViewTitle.setText(userModel.getFullName())
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener.onUserItemClicked(userModel)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position
    }

    fun setUsersCollection(usersCollection: MutableCollection<UserModel?>?) {
        validateUsersCollection(usersCollection)
        this.usersCollection = usersCollection as MutableList<UserModel?>?
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    private fun validateUsersCollection(usersCollection: MutableCollection<UserModel?>?) {
        requireNotNull(usersCollection) { "The list cannot be null" }
    }

    internal class UserViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.title)
        var textViewTitle: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        usersCollection = emptyList()
    }
}

package info.firozansari.data.repository.datasource;

import android.content.Context;
import androidx.annotation.NonNull;
import info.firozansari.data.cache.UserCache;
import info.firozansari.data.entity.mapper.UserEntityJsonMapper;
import info.firozansari.data.net.RestApi;
import info.firozansari.data.net.RestApiImpl;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 */
@Singleton
public class UserDataStoreFactory {

  private final Context context;
  private final UserCache userCache;

  @Inject
  UserDataStoreFactory(@NonNull Context context, @NonNull UserCache userCache) {
    this.context = context.getApplicationContext();
    this.userCache = userCache;
  }

  /**
   * Create {@link UserDataStore} from a user id.
   */
  public UserDataStore create(int userId) {
    UserDataStore userDataStore;

    if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
      userDataStore = new DiskUserDataStore(this.userCache);
    } else {
      userDataStore = createCloudDataStore();
    }

    return userDataStore;
  }

  /**
   * Create {@link UserDataStore} to retrieve data from the Cloud.
   */
  public UserDataStore createCloudDataStore() {
    final UserEntityJsonMapper userEntityJsonMapper = new UserEntityJsonMapper();
    final RestApi restApi = new RestApiImpl(this.context, userEntityJsonMapper);

    return new CloudUserDataStore(restApi, this.userCache);
  }
}

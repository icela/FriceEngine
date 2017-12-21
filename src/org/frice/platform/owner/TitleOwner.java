package org.frice.platform.owner;

import org.jetbrains.annotations.NotNull;

public interface TitleOwner {
	void setTitle(@NotNull String title);

	@NotNull String getTitle();
}

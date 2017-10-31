package org.frice.platform;

import org.jetbrains.annotations.NotNull;

public interface TitleOwner {
	void setTitle(@NotNull String title);

	@NotNull String getTitle();
}

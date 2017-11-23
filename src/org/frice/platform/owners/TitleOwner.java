package org.frice.platform.owners;

import org.jetbrains.annotations.NotNull;

public interface TitleOwner {
	void setTitle(@NotNull String title);

	@NotNull String getTitle();
}



class SuperBlock {
	int size;
    int iSize;
	long freeList;

	public String toString () {
		return
			"SUPERBLOCK:\n"
			+ "Size: " + size
			+ "  Isize: " + iSize
			+ "  freeList: " + freeList;
	}
}

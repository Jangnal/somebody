package common;

public class PageVO {

	int totalist ; //총글의 건수
	int totalPage ; //총 페이지수
	int totalBlock ; //총 블럭수
	int pageList = 10 ; //페이지당 보여질 목록의 수
	int blockPage = 10; //블럭당 보여질 페이지의 수
	int curPage  ; //현재 페이지
	int beginList , endList ;// 각 페이지에 보여질 목록번호
	int curBlock ; //현재 블럭
	int beginPage , endPage ; //각 블럭에 보여질 페이지 번호
	String search , keyword , viewType = "list";//검색조건 , 검색어 , 보기형태(List,Grid)
	
	
	
	public int getTotalist() {
		return totalist;
	}
	public void setTotalist(int totalist) {
		this.totalist = totalist; 
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalBlock() {
		return totalBlock;
	}
	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}
	public int getPageList() {
		return pageList;
	}
	public void setPageList(int pageList) {
		this.pageList = pageList;
	}
	public int getBlockPage() {
		return blockPage;
	}
	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getBeginList() {
		return beginList;
	}
	public void setBeginList(int beginList) {
		this.beginList = beginList;
	}
	public int getEndList() {
		return endList;
	}
	public void setEndList(int endList) {
		this.endList = endList;
	}
	public int getCurBlock() {
		return curBlock;
	}
	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	
}

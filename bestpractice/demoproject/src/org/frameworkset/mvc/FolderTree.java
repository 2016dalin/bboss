package org.frameworkset.mvc;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;



import com.frameworkset.common.tag.tree.COMTree;
import com.frameworkset.common.tag.tree.itf.ITreeNode;

public class FolderTree extends COMTree {


	private String rootPath = FileController.getWorkFoldPath();//"/data/bpit/www";
	
	
    
	public boolean hasSon(ITreeNode father){
		try {
			
						
			if(father.isRoot()){
				return FileUtil.hasSubDirectory(this.rootPath);
			}else{
				return FileUtil.hasSubDirectory(this.rootPath,father.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	public List getDirectoryResource(String uri)
			 {
		
		List fileResources = new ArrayList();
		File[] subFiles = FileUtil.getSubDirectories(rootPath, uri);
		for (int i = 0; subFiles != null && i < subFiles.length; i++) {
			FileResource fr = new FileResource();
			String theURI = "";
			if (uri != null && uri.trim().length() != 0) {
				uri = uri.replace('\\', '/');
				if (!uri.endsWith("/")) {
					theURI = uri + "/";
				} else {
					theURI = uri;
				}
				if (theURI.trim().equals("/")) {
					theURI = "";
				}
			}
			fr.setUri(theURI + subFiles[i].getName());
			fr.setName(subFiles[i].getName());
			fr.setDirectory(true);
			fileResources.add(fr);
		}
		return fileResources;
	}

	/**
	 * ��Դ������id��ResourceManager
	 * վ��id=site:siteid
	 * Ƶ��id=channelid:site:siteid
	 * Ƶ����id=channel:site:siteid
	 */
	public boolean setSon(ITreeNode father, int curLevel){
		//String fileFlag = (String)session.getAttribute("fileFlag");
		//ͼƬ�����ͼ��list���б�ppt���õ�Ƭ��thumbnail������ͼ
		//String viewertype = request.getParameter("viewertype");
		List fileresources = null;
		String parentPath = "";
		try {
			
			//��Ӷ���վ��
			
				
			parentPath = father.getId();
			
			if(father.isRoot())
			{
				father.setNodeLink("javascript:linktofolder('')");
				parentPath = "";
			}
			
			
			fileresources = getDirectoryResource(parentPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i=0;fileresources!=null&&i<fileresources.size();i++){
			FileResource fr = (FileResource)fileresources.get(i);
			if(fr.isDirectory()){
				Map params = new HashMap();
				String uri = fr.getUri();
			
				
				params.put("nodeLink","javascript:linktofolder('"+uri+"')");
				addNode(father,uri,fr.getName(),"folder",
						true, curLevel, (String)null,(String)null,(String)null,params);
				
			}
		}
		
		return true;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		// TODO Auto-generated method stub
		super.setPageContext(pageContext);
		
		
	}
}

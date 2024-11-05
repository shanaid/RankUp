  import React, { useState, useEffect } from 'react';
  import { FaAngleLeft, FaAngleRight, FaEllipsisH, FaHeart } from 'react-icons/fa';
  import { useDispatch, useSelector } from 'react-redux';
  import { fetchFeedList, postComment, deleteComment, editComment, deleteFeed, editFeed, likePost  } from '../../store/feedListSlice';
  import { useNavigate, useLocation } from 'react-router-dom';
  import { fetchUserData } from '../../store/userSlice';
  import './FeedList.css';
  import '../../assets/font/Font.css' 
  import d from "../../assets/images/ddddd.png"


  const FeedList = () => {
    const dispatch = useDispatch();
    const token = useSelector((state) => state.auth.token);
    const feedList = useSelector((state) => state.feedList.feeds);
    const feedListStatus = useSelector((state) => state.feedList.status);
    const user = useSelector((state) => state.user);
    const { email } = user;
    
    const navigate = useNavigate();
    const location = useLocation();

    const [currentIndex, setCurrentIndex] = useState(0);
    const [newComment, setNewComment] = useState('');
    const [editingComment, setEditingComment] = useState(null);
    const [editedComment, setEditedComment] = useState('');
    const [showOptions, setShowOptions] = useState(null);
    const [isEditingFeed, setIsEditingFeed] = useState(false);
    const [editedFeedContent, setEditedFeedContent] = useState('');

    useEffect(() => {
      if (feedListStatus === 'idle' && token) {
        dispatch(fetchFeedList(token)); 
      }
    }, [feedListStatus, dispatch, token]);

    useEffect(() => {
      const searchParams = new URLSearchParams(location.search);
      const index = parseInt(searchParams.get('post'), 10);
      if (!isNaN(index) && index >= 0 && index < feedList.length) {
        setCurrentIndex(index);
      }
    }, [location.search, feedList.length]);

    useEffect(() => {
      if (token) {
        dispatch(fetchUserData(token));
      }
    }, [dispatch, token]);

    const handlePrevClick = () => {
      setCurrentIndex((prevIndex) => (prevIndex === 0 ? feedList.length - 1 : prevIndex - 1));
    };

    const handleNextClick = () => {
      setCurrentIndex((prevIndex) => (prevIndex === feedList.length - 1 ? 0 : prevIndex + 1));
    };

    const handleCommentChange = (e) => {
      setNewComment(e.target.value);
    };

    const handleCommentSubmit = (e) => {
      e.preventDefault();
      if (newComment.trim() !== '') {
        dispatch(postComment({
          token,
          feedId: feedList[currentIndex].feedId,
          content: newComment
        })).then(() => {
          navigate(`?post=${currentIndex}`);
          window.location.reload();
        });
      }
    };

    const handleEditCommentChange = (e) => {
      setEditedComment(e.target.value);
    };

    const handleCommentEdit = (index) => {
      setEditingComment(index);
      setEditedComment(feedList[currentIndex].comments[index].comment);
      setShowOptions(null);
    };

    const handleCommentSave = (index) => {
      const feedComentId = feedList[currentIndex].comments[index].feedComentId;
      dispatch(editComment({
        token,
        feedComentId,
        updatedComment: {
          nickname: feedList[currentIndex].comments[index].nickname,
          content: editedComment,
          createdAt: new Date().toISOString(),
        },
      })).then(() => {
        setEditingComment(null);
        setEditedComment('');
        navigate(`?post=${currentIndex}`);
        window.location.reload();
      });
    };
    

    const handleCommentDelete = (index) => {
      const feedComentId = feedList[currentIndex].comments[index].feedComentId;
      dispatch(deleteComment({
        token,
        feedComentId
      })).then(() => {
        setShowOptions(null);
      });
    };

    const handleShowOptions = (index) => {
      setShowOptions(showOptions === index ? null : index);
    };

    const handleLikeClick = () => {
      const feedId = feedList[currentIndex].feedId;
      dispatch(likePost({ token, feedId }));
    };

    const handleContEdit = () => {
      setIsEditingFeed(true);
      setEditedFeedContent(feedList[currentIndex].caption);
    };

    const handleFeedCancel = () => {
      setIsEditingFeed(false);
      setEditedFeedContent('');
      setShowOptions(null);
    };

    const handleFeedSave = () => {
      const feedId = feedList[currentIndex].feedId;
      dispatch(editFeed({ token, feedId, content: editedFeedContent }))
        .then(() => {
          setIsEditingFeed(false);
          setShowOptions(null);
          navigate(`?post=${currentIndex}`);
          window.location.reload();
        });
    };

    const handleContDel = () => {
      const feedId = feedList[currentIndex].feedId;
      dispatch(deleteFeed({ token, feedId }))
        .then(() => {
          setCurrentIndex(0);
          setShowOptions(null);
          // console.log("삭제성공")
        });
    };

    console.log(feedList)

    return (
      <div className="FeedList">
        <button className="arrowleft" onClick={handlePrevClick}>
          <FaAngleLeft />
        </button>      
        
        {feedList.length > 0 && (
          <div className="FeedListPhoto">
            <div className="FeedListPhotoHeader">
              <span className="FeedListPhotoauthor">{feedList[currentIndex].nickname}</span>
              <span className="FeedListPhototime">{new Date(feedList[currentIndex].lastCreateTime).toLocaleString()}</span>
            </div>
            
            {isEditingFeed ? (
              <textarea
                className="FeedEditTextarea"
                value={editedFeedContent}
                onChange={(e) => setEditedFeedContent(e.target.value)}
              />
            ) : (
              <>
                {/* <img src={feedList[currentIndex].img} alt="feed" className="FeedListImg" /> */}
                <img src={feedList[currentIndex].img || d} alt="feed" className="FeedListImg" />
                <p className="FeedListCaption">{feedList[currentIndex].caption}</p>
              </>
            )}
            
            <div className="FeedListPhotolikeSection">
              {feedList[currentIndex].myLike ? (
                <FaHeart onClick={handleLikeClick} className="FeedListPhotolikeButtonX" />
              ) : (
                <FaHeart onClick={handleLikeClick} className="FeedListPhotolikeButton" />
              )}
              <span>{feedList[currentIndex].likes}</span>
      
              {feedList[currentIndex].email === email && (
                <div className="FeedListMyContentWrapper">
                  <FaEllipsisH className="FeedListMyContent" onClick={() => handleShowOptions(currentIndex)} />
                  {showOptions === currentIndex && (
                    <div className="FeedListOptionsDropdown">
                      {isEditingFeed ? (
                        <>
                          <button onClick={handleFeedSave}>저장</button>  
                          <button onClick={handleFeedCancel}>취소</button>
                        </>
                      ) : (
                        <>
                          <button onClick={handleContEdit}>수정</button>
                          <button onClick={handleContDel}>삭제</button>
                        </>
                      )}
                    </div>
                  )}
                </div>
              )}
            </div>
          </div>
        )}

        <div className="FeedListCom">
          <form onSubmit={handleCommentSubmit} className="FeedListcommentForm">
            <input
              type="text"
              value={newComment}
              onChange={handleCommentChange}
              placeholder="댓글을 입력하세요..."
              className="FeedListCommentInput"
            />
            <button type="submit" className="commentButton">등록</button>
          </form>
          <ul className="commentsList">
            {feedList[currentIndex]?.comments.map((comment, index) => (
              <li key={index} className="commentItem">
                {editingComment === index ? (
                  <div className="editCommentContainer">
                    <input
                      type="text"
                      value={editedComment}
                      onChange={handleEditCommentChange}
                      className="editCommentInput"
                    />
                    <button onClick={() => handleCommentSave(index)} className="saveButton">저장</button>
                  </div>
                ) : (
                  <>
                    <div className="commentHeader">
                      <span className="commentAuthor">{comment.nickname}</span>
                      <span className="commentTime">
                      {new Date(comment.time).toLocaleString('ko-KR', {
                          year: 'numeric',
                          month: '2-digit',
                          day: '2-digit',
                          hour: '2-digit',
                          minute: '2-digit',
                          second: '2-digit',
                        }).replace(/\. /g, '. ')}
                      </span>
                    </div>
                    <div className="commentBody">
                      <span className="commentText">{comment.comment}</span>
                      <FaEllipsisH onClick={() => handleShowOptions(index)} className="ellipsisButton" />
                    </div>
                  </>
                )}
                {showOptions === index && (
                  <div className="optionsDropdown">
                    <button onClick={() => handleCommentEdit(index)} className="editButton">수정</button>
                    <button onClick={() => handleCommentDelete(index)} className="deleteButton">삭제</button>
                  </div>
                )}
              </li>
            ))}
          </ul>
        </div>

        <button className="arrowright" onClick={handleNextClick}>
          <FaAngleRight />
        </button>
      </div>
    );
  };

  export default FeedList;
